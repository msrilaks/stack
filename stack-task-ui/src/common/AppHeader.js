import React, { Component } from 'react';
import { Link, NavLink } from 'react-router-dom';
import './AppHeader.css';

class AppHeader extends Component {
    render() {
        return (
            <header className="app-header">
                <div className="container">
                    <div className="app-branding">
                        <Link to="/stack" className="app-title">Stack It Down</Link>
                    </div>
                    <div className="app-options">
                        <nav className="app-nav">
                                { this.props.authenticated ? (
                                    <ul>
                                        <li>
                                            <a onClick={this.props.onLogout}>Logout</a>
                                        </li>
                                        <li>
                                        <div className="app-profile-avatar">
                                        { 
                                            this.props.currentUser && this.props.currentUser.imageUrl ? (
                                                <img src={this.props.currentUser.imageUrl} alt={this.props.currentUser.name}/>
                                            ) : (
                                                <div className="text-avatar">
                                                    <span>{this.props.currentUser.name && this.props.currentUser.name[0]}</span>
                                                </div>
                                            )
                                        }
                                        </div>
                                            {/* <NavLink to="/profile">Profile</NavLink> */}
                                        </li>
                                        
                                    </ul>
                                ): (
                                    <ul>
                                        <li>
                                            <NavLink to="/login">Login</NavLink>        
                                        </li>
                                        <li>
                                            <NavLink to="/signup">Signup</NavLink>        
                                        </li>
                                    </ul>
                                )}
                        </nav>
                    </div>
                </div>
            </header>
        )
    }
}

export default AppHeader;