import React, { Component } from 'react';
import './TaskList.css';
import Task from '../../stack/task/Task';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import { styles } from '../../util/APIUtils';
import Paper from '@material-ui/core/Paper';
import CardMedia from '@material-ui/core/CardMedia';
  
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
                <div className="task-empty-details">
                    <Paper >
                <Card>
                <CardActionArea>
                <CardContent>
                    <Typography variant="h6" color="textSecondary" component="h2">
                    <div style={styles.taskEmpty} >You have no pending tasks!</div> 
                    </Typography>
                </CardContent>
                </CardActionArea>
                <CardActions className="task-button-panel">
                </CardActions>
            </Card>
            </Paper>
                </div>
            )
            }
            </div>
            );
    }
}

export default TaskList