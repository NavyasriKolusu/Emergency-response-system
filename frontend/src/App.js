import React, { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

const API = "http://localhost:8082/api/emergencies";

function App() {
  const [list, setList] = useState([]);
  const [filter, setFilter] = useState("ALL");

  const [form, setForm] = useState({
    type: "",
    description: "",
    location: "",
    customType: ""
  });

  useEffect(() => {
    loadData();

    const interval = setInterval(() => {
      loadData();
    }, 5000);

    return () => clearInterval(interval);
  }, []);

  const loadData = () => {
    axios.get(API)
      .then(res => setList(res.data))
      .catch(err => console.log(err));
  };

  const createEmergency = () => {
    const finalType = form.type === "other" ? form.customType : form.type;

    if (!finalType || !form.description || !form.location) {
      alert("Please fill all fields");
      return;
    }

    axios.post(API, {
      type: finalType,
      description: form.description,
      location: form.location,
      createdBy: 1
    }).then(() => {
      alert("Emergency created successfully");
      setForm({
        type: "",
        description: "",
        location: "",
        customType: ""
      });
      loadData();
    });
  };

  const accept = (id) => {
    axios.put(`${API}/${id}/accept`).then(loadData);
  };

  const resolve = (id) => {
    axios.put(`${API}/${id}/resolve`).then(loadData);
  };

  // 📊 Stats
  const total = list.length;
  const pending = list.filter(e => e.status === "PENDING").length;
  const accepted = list.filter(e => e.status === "ACCEPTED").length;
  const resolved = list.filter(e => e.status === "RESOLVED").length;

  // 🔍 FILTER LOGIC
  const filteredList =
    filter === "ALL"
      ? list
      : list.filter(e => e.status === filter);

  return (
    <div className="container">

      {/* HEADER */}
      <div className="header">
        <h1>Emergency response system</h1>
        <p>Monitor and manage emergency requests</p>
      </div>

      {/* STATS */}
      <div className="stats">
        <div className="stat-card"><h3>Total</h3><p>{total}</p></div>
        <div className="stat-card"><h3>Pending</h3><p>{pending}</p></div>
        <div className="stat-card"><h3>Accepted</h3><p>{accepted}</p></div>
        <div className="stat-card"><h3>Resolved</h3><p>{resolved}</p></div>
      </div>

      {/* FORM */}
      <div className="card">
        <h2>Create Emergency</h2>

        <select
          value={form.type}
          onChange={e => setForm({ ...form, type: e.target.value })}
        >
          <option value="">Select Type</option>
          <option value="fire">Fire</option>
          <option value="accident">Accident</option>
          <option value="blood">Blood</option>
          <option value="medical">Medical</option>
          <option value="other">Other</option>
        </select>

        {form.type === "other" && (
          <input
            placeholder="Enter custom type"
            value={form.customType}
            onChange={e => setForm({ ...form, customType: e.target.value })}
          />
        )}

        <input
          placeholder="Enter description"
          value={form.description}
          onChange={e => setForm({ ...form, description: e.target.value })}
        />

        <input
          placeholder="Enter location"
          value={form.location}
          onChange={e => setForm({ ...form, location: e.target.value })}
        />

        <button onClick={createEmergency} className="submit-btn">
          Submit Emergency
        </button>
      </div>

      {/* TABLE */}
      <div className="card">
        <div className="table-header">
          <h2>All Emergencies</h2>

          {/* FILTER DROPDOWN */}
          <select
            className="filter"
            value={filter}
            onChange={e => setFilter(e.target.value)}
          >
            <option value="ALL">All</option>
            <option value="PENDING">Pending</option>
            <option value="ACCEPTED">Accepted</option>
            <option value="RESOLVED">Resolved</option>
          </select>
        </div>

        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Type</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {filteredList.length === 0 ? (
              <tr>
                <td colSpan="4">No data found</td>
              </tr>
            ) : (
              filteredList.map(e => (
                <tr key={e.id}>
                  <td>{e.id}</td>
                  <td>{e.type}</td>

                  <td>
                    <span className={`status ${e.status.toLowerCase()}`}>
                      {e.status}
                    </span>
                  </td>

                  <td>
                    <div className="actions">
                      <button
                        className="accept"
                        onClick={() => accept(e.id)}
                        disabled={e.status !== "PENDING"}
                      >
                        Accept
                      </button>

                      <button
                        className="resolve"
                        onClick={() => resolve(e.id)}
                        disabled={e.status !== "ACCEPTED"}
                      >
                        Resolve
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

    </div>
  );
}

export default App;