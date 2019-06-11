import React, { Component } from 'react';
import './TaskList.css';
import Task from '../../stack/task/Task';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import DeleteIcon from '@material-ui/icons/Delete';
import DoneIcon from '@material-ui/icons/Done';
import CreateIcon from '@material-ui/icons/Create';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import ShareIcon from '@material-ui/icons/Share';
  
class TaskList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: 0,
        };
        this.handleChange = this.handleChange.bind(this);
    }
     
    handleChange(event, newValue) {
        this.setState({value:newValue});
      }
    render() {
        return ( 
            <div>
            
        <Paper>
            <Tabs value={this.state.value}
        onChange={this.handleChange}
        variant="fullWidth"
        indicatorColor="primary"
        textColor="primary">
               <Tab icon={<CreateIcon />} label="Active"></Tab>
               <Tab icon={<DoneIcon />} label="Completed"/>
               <Tab icon={<ShareIcon />} label="Re-assigned"/>
               <Tab icon={<DeleteIcon />} label="Deleted"/>

            </Tabs>
        </Paper>
{
    
(this.props.tasks && Object.keys(this.props.tasks).length > 0) ? (
Object.entries(this.props.tasks).map(([key, item])=>(
    
        (item.deletedTimeStamp ===null)? (this.state.value === 0 &&
                <div name={item.id}>
                    <Task  authenticated={this.props.authenticated} 
                    currentUser={this.props.currentUser} 
                    stack={this.props.stack}
                    task={item} 
                    taskIndex= {key}
                    reloadTasksFunc={this.props.reloadTasks}/>
                
                </div>
        ):
        (this.state.value === 3  && item.deletedTimeStamp !==null &&
            <div name={item.id}>
                <Task  authenticated={this.props.authenticated} 
                currentUser={this.props.currentUser} 
                stack={this.props.stack}
                task={item} 
                taskIndex= {key}
                reloadTasksFunc={this.props.reloadTasks}/>
            
            </div>
    )
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