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
            deleteTask: false,
        };
        this.onButtonDeleteTaskClicked = this.onButtonDeleteTaskClicked.bind(this);
    }

    onButtonDeleteTaskClicked() {
        this.setState({
            deleteTask: true,
        });
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
        return (
            <Card>
                <CardActionArea>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                    {this.props.task.label }<span className="task-label">{this.props.taskIndex}  category:{this.props.task.category}   isDeleted: { this.props.task.deletedTimeStamp } </span>
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                    { this.props.task.description }
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