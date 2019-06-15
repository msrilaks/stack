import React, { Component } from 'react';
import './Task.css';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Alert from 'react-s-alert';
import { deleteTask, patchTask, styles } from '../../util/APIUtils';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import ShareIcon from '@material-ui/icons/Share';
import DoneIcon from '@material-ui/icons/Done';
import ClearIcon from '@material-ui/icons/Clear';
import UndoIcon from '@material-ui/icons/Undo';
import Paper from '@material-ui/core/Paper';
import AlarmIcon from '@material-ui/icons/Alarm';
  
class Task extends Component {
    constructor(props) {
        super(props);
        this.onButtonDeleteTaskClicked = this.onButtonDeleteTaskClicked.bind(this);
        this.onButtonCompleteTaskClicked = this.onButtonCompleteTaskClicked.bind(this);
        this.onButtonTodoTaskClicked = this.onButtonTodoTaskClicked.bind(this);
    }

    onButtonTodoTaskClicked() {
        const completeTaskRequest = Object.assign({}, this.props.task);
        console.log(completeTaskRequest);
        patchTask(completeTaskRequest,"isToDo=true")
        .then((response) => {
            this.setState({
                },function () {
                    console.log("PatchTaskRequest "+this.state.task);
                });
            Alert.success("Task pushed back into your stack successfully!");
            this.props.reloadTasksFunc();
        }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }

    onButtonCompleteTaskClicked() {
        const completeTaskRequest = Object.assign({}, this.props.task);
        console.log(completeTaskRequest);
        patchTask(completeTaskRequest,"isCompleted=true")
        .then((response) => {
            this.setState({
                },function () {
                    console.log("PatchTaskRequest "+this.state.task);
                });
            Alert.success("Task marked completed successfully!");
            this.props.reloadTasksFunc();
        }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }

    onButtonDeleteTaskClicked() {
        const deleteTaskRequest = Object.assign({}, this.props.task);
        console.log(deleteTaskRequest);
        deleteTask(deleteTaskRequest)
        .then((response) => {
            this.setState({
                },function () {
                    console.log("onButtonDeleteTaskClicked "+this.state.task);
                });
            Alert.success("Task deleted successfully!");
            this.props.reloadTasksFunc();
        }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }

    render() {
        let TaskButtonPanel;
        if(this.props.taskProfile == 'todo') {
           TaskButtonPanel = <div>
               <IconButton aria-label="Complete" onClick={this.onButtonCompleteTaskClicked}>
                    <DoneIcon style={styles.taskIcon}/>
                </IconButton>
                <IconButton aria-label="Push">
                    <ShareIcon style={styles.taskIcon}/>
                </IconButton>
                <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                    <DeleteIcon style={styles.taskIcon}/>
                </IconButton>
            </div>
        } else if(this.props.taskProfile == 'deleted'){
            TaskButtonPanel = <div>
            <IconButton aria-label="Clear">
                 <ClearIcon style={styles.taskIcon}/>
             </IconButton>
         </div>
        } else if(this.props.taskProfile == 'pushed'){
            TaskButtonPanel = <div>
            <IconButton aria-label="Remind">
                <AlarmIcon style={styles.taskIcon}/>
            </IconButton>
            <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                <DeleteIcon style={styles.taskIcon}/>
            </IconButton>
         </div>
        }else if(this.props.taskProfile == 'completed'){
            TaskButtonPanel = <div>
            <IconButton aria-label="Undo" onClick={this.onButtonTodoTaskClicked}>
                 <UndoIcon style={styles.taskIcon}/>
             </IconButton>
             <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                    <DeleteIcon style={styles.taskIcon}/>
            </IconButton>
         </div>
        }
        return (
            <Paper>
            <Card className="task-container">
                <CardActionArea>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {this.props.task.label }<span className="task-description"> { this.props.task.description }</span>
                    </Typography>
                    {/* <span className="task-label"> category:{this.props.task.category} </span> */}
                </CardContent>
                </CardActionArea>
                <CardActions className="task-button-panel">
                    {TaskButtonPanel}
                </CardActions>
            </Card>
            </Paper>
        );
    }
}

export default Task