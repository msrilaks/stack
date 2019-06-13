import React, { Component } from 'react';
import './Task.css';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Alert from 'react-s-alert';
import { deleteTask, styles } from '../../util/APIUtils';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import ShareIcon from '@material-ui/icons/Share';
import DoneIcon from '@material-ui/icons/Done';

  
class Task extends Component {
    constructor(props) {
        super(props);
        this.onButtonDeleteTaskClicked = this.onButtonDeleteTaskClicked.bind(this);
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
               <IconButton aria-label="Complete">
                    <DoneIcon style={styles.taskIcon}/>
                </IconButton>
                <IconButton aria-label="Assign">
                    <ShareIcon style={styles.taskIcon}/>
                </IconButton>
                <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                    <DeleteIcon style={styles.taskIcon}/>
                </IconButton>
            </div>
        }
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
                <CardActions className="task-button-panel">
{TaskButtonPanel}
                
                </CardActions>
            </Card>
        );
    }
}

export default Task