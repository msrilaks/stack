import React, { Component } from 'react';
import Create from '../stack/create/Create';
import Task from '../stack/task/Task';
import './Stack.css';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import { getTasks } from '../util/APIUtils';
import Alert from 'react-s-alert';

class Stack extends Component {

    constructor(props) {
        super(props);
        console.log({props});
        this.state = {
            tasks: this.props.stack.tasks,
            showCreateTask: false,
        }
        
        this.onButtonCreateTaskClicked = this.onButtonCreateTaskClicked.bind(this);
        this.reloadTasks = this.reloadTasks.bind(this);
    }
    componentWillMount() {
          this.setState({
            tasks:this.props.stack.tasks,
         },function () {
            console.log("stackTasks : "+ this.state.tasks)
        });
       }

    reloadTasks() {
        getTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                tasks: response, 
                showCreateTask:false,
                },function () {
                    console.log("stackTasks : "+ this.state.stackTasks)
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
                    <div className="stack-create-task">
                    <Fab size="small" color="primary" aria-label="Add" >
                        <AddIcon onClick={this.onButtonCreateTaskClicked}/>
                    </Fab>
                        {this.state.showCreateTask ?
                            <Create  authenticated={this.props.authenticated} 
                                    currentUser={this.props.currentUser} 
                                    stack={this.props.stack} 
                                    reloadTasksFunc={this.reloadTasks}/>
                                    :null}
                    </div>
                    { 
                               ( this.state.tasks && Object.keys(this.state.tasks).length > 0) ? (
                                    <div>
                                        {
                                            Object.entries(this.state.tasks).map(([key,value])=>(
                                                <div name={key} key={key}>
                                                <Task  authenticated={this.props.authenticated} currentUser={this.props.currentUser} stack={this.props.stack}
                                                task={ this.state.tasks[key]} 
                                                taskIndex= {key}
                                                reloadTasksFunc={this.reloadTasks}/>
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