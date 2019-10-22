import React, { Component } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { styles } from '../util/APIUtils';
import Typography from '@material-ui/core/Typography';

import './AppHeader.css';

class AppHeader extends Component {
    render() {
        return (
            <header className="app-header"  style={styles.appHeader}>
                <div className="container">
                    <div className="app-branding" style={styles.appBranding}>
                    <div style={styles.appBranding}>
                    <img src="/favicon.ico" style={styles.appIcon}/>
                    </div>
                    <div style={styles.appBranding}>
                    <Typography  variant="h5" component="h2">
                        <Link to="/stack" style={styles.appTitle} className="app-title">Stack It Down</Link>
                        </Typography>
                        </div>
                    </div>
                    <div className="app-options">
                        <nav className="app-nav">
                                { this.props.authenticated ? (
                                    <ul>
                                        <li>
                                            <a  style={styles.appLogout} onClick={this.props.onLogout}>Logout</a>
                                        </li>
                                        <li>
                                        <div className="app-profile-avatar">
                                        { 
                                            this.props.currentUser && this.props.currentUser.imageUrl ? (
                                                <img src={this.props.currentUser.imageUrl} alt={this.props.currentUser.name}/>
                                            ) : null
                                        }
                                        </div>
                                            {/* <NavLink to="/profile">Profile</NavLink> */}
                                        </li>
                                        
                                    </ul>
                                ): (
                                    <ul>
                                        <li>
                                            <NavLink to="/login"  style={styles.appLogout}>Login</NavLink>        
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