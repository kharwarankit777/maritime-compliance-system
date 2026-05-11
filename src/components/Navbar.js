import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Navbar.css';

const Navbar = () => {
  const { user, logout, isAdmin, isCrew } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/dashboard" className="navbar-brand">
          Maritime Compliance
        </Link>
        
        <div className="navbar-menu">
          {user && (
            <>
              {isAdmin() && (
                <>
                  <Link to="/dashboard" className="navbar-link">Dashboard</Link>
                  <Link to="/maintenance" className="navbar-link">Maintenance</Link>
                  <Link to="/drills" className="navbar-link">Drills</Link>
                </>
              )}
              
              {isCrew() && (
                <>
                  <Link to="/crew" className="navbar-link">My Tasks</Link>
                  <Link to="/crew" className="navbar-link">My Drills</Link>
                </>
              )}
              
              <span className="navbar-user">Welcome, {user.name}</span>
              <button onClick={handleLogout} className="navbar-logout">
                Logout
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
