import React, { Component } from 'react';
import './Stack.css';

class Stack extends Component {

    constructor(props) {
        super(props);
        console.log({props});
        this.state = {
            tasks: null
          }
    }

    componentWillMount() {
        this.setState({tasks:this.props.stack.tasks},function () {
            console.log(this.state.tasks);
        });
       }

    render() {
        return (
            <div className="stack-container">
                <div className="container">
                    <div className="stack-info">
                            { 
                                this.props.stack.tasks ? (
                                    <div className="stack-task-container">
                                        {
                                            Object.keys(this.state.tasks).map((item, i) => (
                                            <div className="stack-task" key={i}>
                                                <span>{ this.state.tasks[item].description }</span>
                                            </div>
                                            ))
                                        }
                                    </div>
                                    
                                ) : (
                                    <div className="stack-task-container">
                                        <span>You have no pending tasks!</span>
                                    </div>
                                )
                            }
                    </div>
                </div>    
            </div>
        );
    }
}

export default Stack