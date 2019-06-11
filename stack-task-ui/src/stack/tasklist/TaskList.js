import React, { Component } from 'react';
import './TaskList.css';
import Task from '../../stack/task/Task';
  
class TaskList extends Component {
    constructor(props) {
        super(props);
    }
     
    render() {
        return ( this.props.tasks && Object.keys(this.props.tasks).length > 0) ? (
            Object.entries(this.props.tasks).map(([key, value])=>(
                
                <div>
                    {
                        <div name={value.id}>
                            <Task  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            task={value} 
                            taskIndex= {key}
                            reloadTasksFunc={this.props.reloadTasks}/>
                        
                        </div>
                    }
                </div>
                 ))
            ) : (
                <div>You have no pending tasks!</div>
            );
    }
}

export default TaskList