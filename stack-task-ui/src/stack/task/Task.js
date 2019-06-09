import React, { Component } from 'react';
import './Task.css';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Alert from 'react-s-alert';
import { deleteTask } from '../../util/APIUtils';
import { getTask } from '../../util/APIUtils';
  
class Task extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task:{
                stackId:this.props.task.stackId,
                id:this.props.task.id,
                category: this.props.task.category,
                label: this.props.task.label,
                description: this.props.task.description,
                userId: this.props.task.userId,
                createdByUserId: this.props.task.createdByUserId,
                origin: this.props.task.origin,
                createdTimeStamp: this.props.task.createdTimeStamp,
                lastModifiedTimeStamp: this.props.task.lastModifiedTimeStamp,
                completedTimeStamp:this.props.task.completedTimeStamp,
                movedTimeStamp: this.props.task.movedTimeStamp,
                deletedTimeStamp: this.props.task.deletedTimeStamp
            },
            deleteTask: false,

        };
        this.onButtonDeleteTaskClicked = this.onButtonDeleteTaskClicked.bind(this);
    }

    componentWillReceiveProps(props) {
        
        const { task, taskIndex} = props;
      //  console.log("### SAR OLDComponentWillReceiveProps "+taskIndex +" , "+ this.props.taskIndex+" and "+ task.id);
        console.log("### SAR ComponentWillReceiveProps "+taskIndex +" , "+ this.props.taskIndex+" and "+ task.id);
        // getTask(this.state.task.stackId,this.state.task.id)
        //     .then((response) => {
        //         this.setState({
        //             task: response, 
        //             },function () {
        //                 console.log("ComponentWillReceiveProps "+this.state.task);
        //             });
        //     }).catch(error => {
        //         console.log((error && error.message) || 'Oops! Something went wrong. Please try again!');
        //     });
        // if (taskIndex !== this.props.taskIndex) {
            
        // }
      }
    onButtonDeleteTaskClicked() {
        this.setState({
            deleteTask: true,
        });
        const deleteTaskRequest = Object.assign({}, this.state.task);
        console.log(deleteTaskRequest);
        deleteTask(deleteTaskRequest)
        .then((response) => {
            this.setState({
                task: response, 
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
        return (
            <Card>
                <CardActionArea>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                    {this.state.task.label }<span className="task-label">{this.props.taskIndex}  category:{this.state.task.category}   isDeleted: { this.state.task.deletedTimeStamp } </span>
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                    { this.state.task.description }
                    </Typography>
                    </CardContent>
                </CardActionArea>
                <CardActions>
                    <Button size="small" color="primary">
                    Send
                    </Button>
                    <Button size="small" color="primary" onClick={this.onButtonDeleteTaskClicked}>
                    Delete
                    </Button>
                </CardActions>
            </Card>
        );
    }
}

export default Task