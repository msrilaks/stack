import React, { Component } from 'react';
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
                    <div className="stack-task-container">
                    <div className="stack-create-task">
                                        <button onClick={this.onButtonCreateTaskClicked}>+Create</button>
                                        {this.state.showCreateTask ?
                                            <Create  authenticated={this.props.authenticated} currentUser={this.props.currentUser} stack={this.props.stack}/>:
                                            null
                                        }  
                                        </div>
                            { 
                                this.props.stack.tasks && Object.keys(this.props.stack.tasks).length > 0 ? (
                                    <div>
                                        {
                                            Object.keys(this.state.tasks).map((item, i) => (
                                            <div key={i}>
                                                <Task  authenticated={this.props.authenticated} currentUser={this.props.currentUser} stack={this.props.stack}
                                                task={ this.state.tasks[item]}/>
                                            </div>
                                            ))
                                        }
                                    </div>
                                    
                                ) : (

                                        <div>You have no pending tasks!</div>

                                )
                            }
                    </div>
                </div>    
            </div>
        );
    }
}

export default Stack