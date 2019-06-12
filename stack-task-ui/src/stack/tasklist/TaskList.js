import React, { Component } from 'react';
import './TaskList.css';
import Task from '../../stack/task/Task';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import DeleteIcon from '@material-ui/icons/Delete';
import DoneIcon from '@material-ui/icons/Done';
import CreateIcon from '@material-ui/icons/Create';
import ShareIcon from '@material-ui/icons/Share';
import { If, Then, ElseIf, Else } from 'react-if-elseif-else-render';
  
class TaskList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: 0,
            toDoCounter: 0,
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
                <Tab icon={<CreateIcon />} label="To Do"></Tab>
                <Tab icon={<DoneIcon />} label="Completed"/>
                <Tab icon={<ShareIcon />} label="Assigned"/>
                <Tab icon={<DeleteIcon />} label="Deleted"/>

                </Tabs>
            </Paper>
            {
                
            (this.props.tasks && Object.keys(this.props.tasks).length > 0) ? (
            Object.entries(this.props.tasks).map(([key, item])=>(
                
                <div>
                <If condition={item.deletedTimeStamp ===null && this.state.value === 0}>
                   <Then>
                         <div name={item.id}>
                            <Task  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            task={item} 
                            taskIndex= {key}
                            taskProfile='todo'
                            reloadTasksFunc={this.props.reloadTasks}/>
                        </div>
                        {this.state.toDoCounter} = {this.state.toDoCounter} + 1
                    </Then>
                    <ElseIf condition={this.state.value === 1  && item.deletedTimeStamp === null
                         && item.completedTimeStamp !==null}>
                         <div name={item.id}>
                            <Task  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            task={item} 
                            taskIndex= {key}
                            taskProfile='completed'
                            reloadTasksFunc={this.props.reloadTasks}/>
                        </div>
                    </ElseIf>
                    <ElseIf condition={this.state.value === 2  && item.deletedTimeStamp === null
                         && item.movedTimeStamp !==null}>
                         <div name={item.id}>
                            <Task  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            task={item} 
                            taskIndex= {key}
                            taskProfile='moved'
                            reloadTasksFunc={this.props.reloadTasks}/>
                        </div>
                    </ElseIf>
                    <ElseIf condition={this.state.value === 3  && item.deletedTimeStamp !==null}>
                         <div name={item.id}>
                            <Task  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            task={item} 
                            taskIndex= {key}
                            taskProfile='deleted'
                            reloadTasksFunc={this.props.reloadTasks}/>
                        </div>
                    </ElseIf>
                  </If>
                  </div>
                ))
            ) : (
                <div>You have no pending tasks!</div>
            )
            }
            <If condition={this.state.toDoCounter === 0 && this.state.value === 0}>
                <Then>
                <div>You have no pending tasks!</div>
                </Then>
            </If>
            </div>
            );
    }
}

export default TaskList