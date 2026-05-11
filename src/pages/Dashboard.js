import React, { useState, useEffect } from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Legend, Tooltip } from 'recharts';
import api from '../api/axios';
import './Dashboard.css';

const Dashboard = () => {
  const [complianceData, setComplianceData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchComplianceData = async () => {
      try {
        const response = await api.get('/api/compliance/dashboard');
        setComplianceData(response.data);
      } catch (error) {
        console.error('Error fetching compliance data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchComplianceData();
  }, []);

  if (loading) {
    return <div className="loading">Loading dashboard...</div>;
  }

  if (!complianceData) {
    return <div className="error">Error loading compliance data</div>;
  }

  const maintenanceData = [
    { name: 'Completed', value: complianceData.completedTasks || 0, color: '#28a745' },
    { name: 'Pending', value: (complianceData.totalTasks - complianceData.completedTasks) || 0, color: '#ffc107' },
  ];

  return (
    <div className="dashboard">
      <h1>Compliance Dashboard</h1>

      <div className="compliance-cards">
        <div className="card">
          <h3>Maintenance Compliance</h3>
          <div className="card-value">
            {complianceData?.maintenanceCompliancePercent?.toFixed(1) || 0}%
          </div>
        </div>

        <div className="card">
          <h3>Drill Compliance</h3>
          <div className="card-value">
            {complianceData?.drillCompliancePercent?.toFixed(1) || 0}%
          </div>
        </div>

        <div className="card">
          <h3>Overall Compliance</h3>
          <div className="card-value">
            {complianceData?.overallCompliancePercent?.toFixed(1) || 0}%
          </div>
        </div>
      </div>

      <div className="dashboard-grid">
        <div className="card chart-container">
          <h3>Maintenance Status</h3>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={maintenanceData}
                cx="50%"
                cy="50%"
                labelLine={false}
                label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                outerRadius={80}
                fill="#8884d8"
                dataKey="value"
              >
                {maintenanceData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={entry.color} />
                ))}
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>

        <div className="card">
          <h3>Overdue Tasks</h3>
          <div className="list-container">
            {complianceData.overdueTasks > 0 ? (
              <p className="overdue-count">{complianceData.overdueTasks} overdue tasks</p>
            ) : (
              <p>No overdue tasks</p>
            )}
          </div>
        </div>

        <div className="card">
          <h3>Missed Drills</h3>
          <div className="list-container">
            {complianceData.missedDrills > 0 ? (
              <p className="missed-count">{complianceData.missedDrills} missed drills</p>
            ) : (
              <p>No missed drills</p>
            )}
          </div>
        </div>

        <div className="card">
          <h3>Summary</h3>
          <div className="list-container">
            <p>Total Tasks: {complianceData.totalTasks}</p>
            <p>Completed Tasks: {complianceData.completedTasks}</p>
            <p>Total Drills: {complianceData.totalDrills}</p>
            <p>Attended Drills: {complianceData.attendedDrills}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;