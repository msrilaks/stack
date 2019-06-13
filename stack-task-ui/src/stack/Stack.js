import React, { Component } from 'react';
import Create from '../stack/create/Create';
import TaskList from '../stack/tasklist/TaskList';
import './Stack.css';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import { getTasks, getCompletedTasks, getDeletedTasks, getMovedTasks, getTodoTasks } from '../util/APIUtils';
import Alert from 'react-s-alert';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import DeleteIcon from '@material-ui/icons/Delete';
import DoneIcon from '@material-ui/icons/Done';
import CreateIcon from '@material-ui/icons/Create';
import ShareIcon from '@material-ui/icons/Share';
import { If, Then, ElseIf, Else } from 'react-if-elseif-else-render';
import IconButton from '@material-ui/core/IconButton';

class Stack extends Component {

    constructor(props) {
        super(props);
        console.log({props});
        this.state = {
            tasks: this.props.stack.tasks,
            todoTasks: null,
            completedTasks: null,
            movedTasks: null,
            deletedTasks: null,
            showCreateTask: false,
            value: 0,
        }

        this.handleChange = this.handleChange.bind(this);
        this.onButtonCreateTaskClicked = this.onButtonCreateTaskClicked.bind(this);
        this.reloadTasks = this.reloadTasks.bind(this);
    }

         
    handleChange(event, newValue) {
        this.setState({value:newValue});
      }

    componentDidMount() {
          this.setState({
            tasks:this.props.stack.tasks,
            
         },function () {
            console.log("stackTasks : "+ this.state.tasks)
        });
        this.reloadTasks();
       }

    reloadTasks() {
        getTodoTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                todoTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("todoTasks : "+ this.state.todoTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 
        
        getDeletedTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                deletedTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("deletedTasks : "+ this.state.deletedTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 

        getMovedTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                movedTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("movedTasks : "+ this.state.movedTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 

        getCompletedTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                completedTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("completedTasks : "+ this.state.completedTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 
    }


    onButtonCreateTaskClicked() {
        this.setState({
            showCreateTask: !this.state.showCreateTask,
        });
    }

    render() {
        return (
            <div className="stack-container">
                <div className="container">
                    <div className="stack-task-container">
                    
                               
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
                <If condition={this.state.value === 0}>
                   <Then>
                   <div className="stack-create-task">
                    {/* <IconButton aria-label="Add" onClick={this.onButtonCreateTaskClicked}>
                    <AddIcon />
                    </IconButton> */}
                        {this.state.showCreateTask ?
                            <Create  authenticated={this.props.authenticated} 
                                    currentUser={this.props.currentUser} 
                                    stack={this.props.stack} 
                                    reloadTasksFunc={this.reloadTasks}/>
                                    :null}
                    </div>
                   <TaskList tasks={this.state.todoTasks} reloadTasks={this.reloadTasks}
                            authenticated={this.props.authenticated}
                         currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='todo'/>
                   </Then>
                   <ElseIf condition={this.state.value === 1 }>
                         <TaskList tasks={this.state.completedTasks} reloadTasks={this.reloadTasks}
                            authenticated={this.props.authenticated}
                         currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='completed'/>
                    </ElseIf>
                    <ElseIf condition={this.state.value === 2 }>
                         <TaskList tasks={this.state.movedTasks} reloadTasks={this.reloadTasks}
                            authenticated={this.props.authenticated}
                         currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='assigned'/>
                    </ElseIf>
                    <ElseIf condition={this.state.value === 3}>
                    <TaskList tasks={this.state.deletedTasks} reloadTasks={this.reloadTasks}
                            authenticated={this.props.authenticated}
                         currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='deleted'/>
                    </ElseIf>
                </If>
            }
            </div>
        </div>    
     </div>
    );
    }
}

export default Stack