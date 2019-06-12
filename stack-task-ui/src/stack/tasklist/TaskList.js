import React, { Component } from 'react';
import './TaskList.css';
import Task from '../../stack/task/Task';

  
class TaskList extends Component {
    constructor(props) {
        super(props);
       
    }

    render() {
        return ( 
            <div>
            {
            (this.props.tasks && Object.keys(this.props.tasks).length > 0) ? (
            Object.entries(this.props.tasks).map(([key, item])=>(
                <div>
                         <div name={item.id}>
                            <Task  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            task={item} 
                            taskIndex= {key}
                            taskProfile={this.props.taskProfile}
                            reloadTasksFunc={this.props.reloadTasks}/>
                        </div>
                  </div>
                ))
            ) : (
                <div>You have no pending tasks!</div>
            )
            }
            </div>
            );
    }
}

export default TaskList