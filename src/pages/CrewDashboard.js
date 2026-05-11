import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import './CrewDashboard.css';

const CrewDashboard = () => {
  const [tasks, setTasks] = useState([]);
  const [upcomingDrills, setUpcomingDrills] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCrewData();
  }, []);

  const fetchCrewData = async () => {
    try {
      const [tasksResponse, drillsResponse] = await Promise.all([
        api.get('/api/maintenance/tasks/my'),
        api.get('/api/drills/upcoming')
      ]);
      
      setTasks(tasksResponse.data);
      setUpcomingDrills(drillsResponse.data);
    } catch (error) {
      console.error('Error fetching crew data:', error);
    } finally {
      setLoading(false);
    }
  };

  const markTaskComplete = async (taskId) => {
    try {
      await api.put(`/api/maintenance/tasks/${taskId}`, {
        status: 'COMPLETED'
      });
      
      setTasks(tasks.map(task => 
        task.id === taskId 
          ? { ...task, status: 'COMPLETED' }
          : task
      ));
    } catch (error) {
      console.error('Error marking task complete:', error);
    }
  };

  const markDrillAttendance = async (drillId) => {
    try {
      await api.post(`/api/drills/${drillId}/attend`);
      
      setUpcomingDrills(upcomingDrills.map(drill => 
        drill.id === drillId 
          ? { ...drill, attended: true }
          : drill
      ));
    } catch (error) {
      console.error('Error marking drill attendance:', error);
    }
  };

  const getStatusBadge = (status) => {
    const statusClass = status === 'COMPLETED' ? 'status-completed' :
                       status === 'IN_PROGRESS' ? 'status-in-progress' :
                       'status-pending';
    return <span className={`status-badge ${statusClass}`}>{status}</span>;
  };

  const isOverdue = (dueDate) => {
    return new Date(dueDate) < new Date();
  };

  if (loading) {
    return <div className="loading">Loading crew dashboard...</div>;
  }

  return (
    <div className="crew-dashboard">
      <h1>My Dashboard</h1>
      
      <div className="dashboard-grid">
        <div className="card">
          <h2>My Tasks</h2>
          <div className="tasks-list">
            {tasks.length > 0 ? (
              tasks.map(task => (
                <div key={task.id} className={`task-item ${isOverdue(task.dueDate) ? 'overdue' : ''}`}>
                  <div className="task-header">
                    <h3>{task.title}</h3>
                    {getStatusBadge(task.status)}
                  </div>
                  <p className="task-description">{task.description}</p>
                  <div className="task-meta">
                    <span className="due-date">
                      Due: {new Date(task.dueDate).toLocaleDateString()}
                    </span>
                    {task.status !== 'COMPLETED' && (
                      <button 
                        onClick={() => markTaskComplete(task.id)}
                        className="btn btn-success btn-sm"
                      >
                        Mark Complete
                      </button>
                    )}
                  </div>
                </div>
              ))
            ) : (
              <p>No tasks assigned</p>
            )}
          </div>
        </div>

        <div className="card">
          <h2>Upcoming Drills</h2>
          <div className="drills-list">
            {upcomingDrills.length > 0 ? (
              upcomingDrills.map(drill => (
                <div key={drill.id} className="drill-item">
                  <div className="drill-header">
                    <h3>{drill.title}</h3>
                    {drill.attended && (
                      <span className="attended-badge">Attended</span>
                    )}
                  </div>
                  <div className="drill-meta">
                    <span className="drill-date">
                      {new Date(drill.scheduledDate).toLocaleString()}
                    </span>
                    {!drill.attended && (
                      <button 
                        onClick={() => markDrillAttendance(drill.id)}
                        className="btn btn-primary btn-sm"
                      >
                        Mark Attendance
                      </button>
                    )}
                  </div>
                </div>
              ))
            ) : (
              <p>No upcoming drills</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrewDashboard;
