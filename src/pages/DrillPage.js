import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import './DrillPage.css';

const DrillPage = () => {
  const [drills, setDrills] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    title: '',
    drillType: 'FIRE_SAFETY',
    shipId: '',
    scheduledDate: ''
  });

  useEffect(() => {
    fetchDrills();
  }, []);

  const fetchDrills = async () => {
    try {
      const response = await api.get('/api/drills');
      setDrills(response.data);
    } catch (error) {
      console.error('Error fetching drills:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/api/drills', formData);
      setFormData({
        title: '',
        drillType: 'FIRE_SAFETY',
        shipId: '',
        scheduledDate: ''
      });
      setShowForm(false);
      fetchDrills();
    } catch (error) {
      console.error('Error creating drill:', error);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const getDrillTypeLabel = (type) => {
    const types = {
      'FIRE_SAFETY': 'Fire Safety',
      'MAN_OVERBOARD': 'Man Overboard',
      'ABANDON_SHIP': 'Abandon Ship',
      'MEDICAL_EMERGENCY': 'Medical Emergency'
    };
    return types[type] || type;
  };

  if (loading) {
    return <div className="loading">Loading drills...</div>;
  }

  return (
    <div className="drill-page">
      <div className="page-header">
        <h1>Safety Drills</h1>
        <button 
          onClick={() => setShowForm(!showForm)} 
          className="btn btn-primary"
        >
          {showForm ? 'Cancel' : 'Create Drill'}
        </button>
      </div>

      {showForm && (
        <div className="form-card">
          <h3>Create New Drill</h3>
          <form onSubmit={handleSubmit} className="drill-form">
            <div className="form-row">
              <div className="form-group">
                <label>Title</label>
                <input
                  type="text"
                  name="title"
                  value={formData.title}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Ship ID</label>
                <input
                  type="number"
                  name="shipId"
                  value={formData.shipId}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>
            
            <div className="form-row">
              <div className="form-group">
                <label>Drill Type</label>
                <select
                  name="drillType"
                  value={formData.drillType}
                  onChange={handleChange}
                  required
                >
                  <option value="FIRE_SAFETY">Fire Safety</option>
                  <option value="MAN_OVERBOARD">Man Overboard</option>
                  <option value="ABANDON_SHIP">Abandon Ship</option>
                  <option value="MEDICAL_EMERGENCY">Medical Emergency</option>
                </select>
              </div>
              <div className="form-group">
                <label>Scheduled Date</label>
                <input
                  type="datetime-local"
                  name="scheduledDate"
                  value={formData.scheduledDate}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>
            
            <button type="submit" className="btn btn-success">
              Create Drill
            </button>
          </form>
        </div>
      )}

      <div className="table-container">
        <table className="drills-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Type</th>
              <th>Ship</th>
              <th>Scheduled Date</th>
            </tr>
          </thead>
          <tbody>
            {drills.map(drill => (
              <tr key={drill.id}>
                <td>{drill.title}</td>
                <td>{getDrillTypeLabel(drill.drillType)}</td>
                <td>{drill.ship?.name || `Ship ${drill.shipId}`}</td>
                <td>{new Date(drill.scheduledDate).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default DrillPage;
