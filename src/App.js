import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import MaintenancePage from './pages/MaintenancePage';
import DrillPage from './pages/DrillPage';
import CrewDashboard from './pages/CrewDashboard';
import './App.css';

const ProtectedRoute = ({ children, requiredRole }) => {
  const { user, loading } = useAuth();
  if (loading) return <div>Loading...</div>;
  if (!user) return <Navigate to="/login" />;
  if (requiredRole && user.role !== requiredRole) {
    return user.role === 'ADMIN' ? <Navigate to="/dashboard" /> : <Navigate to="/crew" />;
  }
  return children;
};

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <div className="app">
          <Navbar />
          <main className="main-content">
            <Routes>
              <Route path="/" element={<Navigate to="/login" />} />
              <Route path="/login" element={<Login />} />
              <Route path="/dashboard" element={<ProtectedRoute requiredRole="ADMIN"><Dashboard /></ProtectedRoute>} />
              <Route path="/maintenance" element={<ProtectedRoute requiredRole="ADMIN"><MaintenancePage /></ProtectedRoute>} />
              <Route path="/drills" element={<ProtectedRoute requiredRole="ADMIN"><DrillPage /></ProtectedRoute>} />
              <Route path="/crew" element={<ProtectedRoute requiredRole="CREW"><CrewDashboard /></ProtectedRoute>} />
            </Routes>
          </main>
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;