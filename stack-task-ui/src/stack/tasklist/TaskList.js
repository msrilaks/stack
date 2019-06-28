import React, { Component } from 'react';
import './TaskList.css';
import Task from '../../stack/task/Task';
import Typography from '@material-ui/core/Typography';
import { styles } from '../../util/APIUtils';
  
class TaskList extends Component {
    constructor(props) {
        super(props);
       
    }

    render() {
        let EmptyListPanel;
        if(this.props.taskProfile == 'todo') {
            EmptyListPanel = <div>
               <Typography variant="h6" color="textSecondary" component="h2">
                    <div style={styles.taskEmpty} >You have no pending tasks!</div> 
                </Typography>
            </div>
        } else if(this.props.taskProfile == 'deleted'){
            EmptyListPanel = <div>
               <Typography variant="h6" color="textSecondary" component="h2">
                    <div style={styles.taskEmpty} >No deleted tasks to list!</div> 
                </Typography>
            </div>
        } else if(this.props.taskProfile == 'pushed'){
            EmptyListPanel = <div>
               <Typography variant="h6" color="textSecondary" component="h2">
                    <div style={styles.taskEmpty} >You have not pushed any tasks to your friends!</div> 
                </Typography>
            </div>
        }else if(this.props.taskProfile == 'completed'){
            EmptyListPanel = <div>
               <Typography variant="h6" color="textSecondary" component="h2">
                    <div style={styles.taskEmpty} >You have no completed tasks!</div> 
                </Typography>
            </div>
        }
        return ( 
            <div>
            {
            (this.props.tasks && Object.keys(this.props.tasks).length > 0) ? (
            Object.entries(this.props.tasks).map(([key, item])=>(
                <div key={item.id}>
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
                <div className="task-empty-details">
                    {EmptyListPanel}
                </div>
            )
            }
            </div>
            );
    }
}

export default TaskList