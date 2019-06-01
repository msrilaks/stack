import React, { Component } from 'react';
import './Stack.css';

class Stack extends Component {
    constructor(props) {
        super(props);
        console.log(props);
    }
    render() {
        return (
            <div className="stack-container">
                <div className="container">
                    <div className="stack-info">
                        <div className="stack-name">
                           <h2>{this.props.stack.userId}</h2>
                           <p className="stack-email">{this.props.stack.userId}</p>
                        </div>
                    </div>
                </div>    
            </div>
        );
    }
}

export default Stack