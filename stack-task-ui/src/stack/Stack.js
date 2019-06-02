import React, { Component } from 'react';
import { Link, NavLink } from 'react-router-dom';
import Create from '../stack/create/Create';
import Task from '../stack/task/Task';
import './Stack.css';


class Stack extends Component {

    constructor(props) {
        super(props);
        console.log({props});
        this.state = {
            tasks: null,
            showCreateTask: false,
        }
        this.onButtonCreateTaskClicked = this.onButtonCreateTaskClicked.bind(this);
    }

    onButtonCreateTaskClicked() {
        this.setState({
            showCreateTask: true,
        });
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
                                        <div>
                                        <button onClick={this.onButtonCreateTaskClicked}>+Create</button>
                                        {this.state.showCreateTask ?
                                            <Create  authenticated={this.props.authenticated} currentUser={this.props.currentUser} stack={this.props.stack}/>:
                                            null
                                        }  
                                        </div>
                                        {
                                            
                                            Object.keys(this.state.tasks).map((item, i) => (
                                            <div key={i}>
                                                <Task  authenticated={this.props.authenticated} currentUser={this.props.currentUser} stack={this.props.stack}
                                                task={ this.state.tasks[item]}/>
                                                {/*<div>{ this.state.tasks[item].category }</div>
                                                <div>{ this.state.tasks[item].label }</div>
                                            <div>{ this.state.tasks[item].description }</div>*/}
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