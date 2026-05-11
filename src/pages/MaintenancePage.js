import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import './MaintenancePage.css';

const MaintenancePage = () => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    shipId: '',
    assignedToUserId: '',
    dueDate: ''
  });

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await api.get('/api/maintenance/tasks');
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/api/maintenance/tasks', formData);
      setFormData({
        title: '',
        description: '',
        shipId: '',
        assignedToUserId: '',
        dueDate: ''
      });
      setShowForm(false);
      fetchTasks();
    } catch (error) {
      console.error('Error creating task:', error);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const getStatusBadge = (status) => {
    const statusClass = status === 'COMPLETED' ? 'status-completed' :
                       status === 'IN_PROGRESS' ? 'status-in-progress' :
                       'status-pending';
    return <span className={`status-badge ${statusClass}`}>{status}</span>;
  };

  if (loading) {
    return <div className="loading">Loading maintenance tasks...</div>;
  }

  return (
    <div className="maintenance-page">
      <div className="page-header">
        <h1>Maintenance Tasks</h1>
        <button 
          onClick={() => setShowForm(!showForm)} 
          className="btn btn-primary"
        >
          {showForm ? 'Cancel' : 'Create Task'}
        </button>
      </div>

      {showForm && (
        <div className="form-card">
          <h3>Create New Task</h3>
          <form onSubmit={handleSubmit} className="task-form">
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
            
            <div className="form-group">
              <label>Description</label>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleChange}
                rows="3"
                required
              />
            </div>
            
            <div className="form-row">
              <div className="form-group">
                <label>Assigned To User ID</label>
                <input
                  type="number"
                  name="assignedToUserId"
                  value={formData.assignedToUserId}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Due Date</label>
                <input
                  type="date"
                  name="dueDate"
                  value={formData.dueDate}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>
            
            <button type="submit" className="btn btn-success">
              Create Task
            </button>
          </form>
        </div>
      )}

      <div className="table-container">
        <table className="tasks-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Ship</th>
              <th>Assigned To</th>
              <th>Status</th>
              <th>Due Date</th>
            </tr>
          </thead>
          <tbody>
            {tasks.map(task => (
              <tr key={task.id}>
                <td>{task.title}</td>
                <td>{task.ship?.name || `Ship ${task.shipId}`}</td>
                <td>{task.assignedTo?.name || `User ${task.assignedToUserId}`}</td>
                <td>{getStatusBadge(task.status)}</td>
                <td>{new Date(task.dueDate).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default MaintenancePage;
