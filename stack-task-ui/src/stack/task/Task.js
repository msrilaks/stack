import React, { Component } from 'react';
import './Task.css';
import Create from '../create/Create';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Alert from 'react-s-alert';
import { deleteTask, patchTask, getPhotos, base64toBlob, styles } from '../../util/APIUtils';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import CalendarIcon from '@material-ui/icons/CalendarToday';
import CloudDownloadIcon from '@material-ui/icons/CloudDownload';
import EditIcon from '@material-ui/icons/Edit';
import DoneIcon from '@material-ui/icons/Done';
import ClearIcon from '@material-ui/icons/Clear';
import UndoIcon from '@material-ui/icons/Undo';
import Paper from '@material-ui/core/Paper';
import AlarmIcon from '@material-ui/icons/Alarm';
import { Link } from "react-router-dom";
import { create } from 'domain';
  
class Task extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isModifyClicked: false,
        }
        this.state = {
             files: []
        };
        this.onButtonDeleteTaskClicked = this.onButtonDeleteTaskClicked.bind(this);
        this.onButtonCompleteTaskClicked = this.onButtonCompleteTaskClicked.bind(this);
        this.onButtonTodoTaskClicked = this.onButtonTodoTaskClicked.bind(this);
        this.onButtonModifyTaskClicked = this.onButtonModifyTaskClicked.bind(this);
        this.reloadTask = this.reloadTask.bind(this);
        this.loadFiles = this.loadFiles.bind(this);
    }
    componentDidMount() {
        this.setState({
            isModifyClicked: false,
        });
        this.loadFiles();
    }

    loadFiles() {
        getPhotos(this.props.stack.id, this.props.task.id)
        .then(response => {
        const responseFiles = (Object.entries(response).map(([key, file]) =>(
                        Object.assign(file, {
                        preview: URL.createObjectURL(base64toBlob(file.image,'')
                        ),
                        id: key,
                        title:file.title
                        })
                        )))
            this.setState({
                //files: response,
                files:responseFiles,
                },function () {
                    this.state.files.map(file => console.log("SRI "+ file.id
                    +" , " +file.preview))
                });
          }).catch(error => {
                Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
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

    onButtonModifyTaskClicked() {
        this.setState({
            isModifyClicked: true,
        },function () {
            console.log("onButtonModifyTaskClicked "+this.state.isModifyClicked);
        });
    }

    reloadTask() {
        this.setState({
            isModifyClicked: false,
        },function () {
            console.log("reloadTask "+this.state.isModifyClicked);
        });

    }

    render() {
        const prevfiles  =  (Object.entries(this.state.files).map(([key, file])=>(
            Object.assign(file, { preview: URL.createObjectURL(base64toBlob(file
            .image,''))}))))
        const files  =  (
            Object.entries(prevfiles).map(([key, file])=>(

            <div style={styles.thumb} key={file.id}>
                 <a href={file.preview} download={file.title} style={styles
                        .thumbIcon}>
                    <IconButton aria-label="CloudDownload" style={styles
                    .photoButtonIcon}>
                        <CloudDownloadIcon style={styles.taskIcon}/>
                    </IconButton>
                 </a>
                <div style={styles.thumbInner}>
                    <img
                        src={file.preview}
                        name={file.title}
                        style={styles.img}
                    />
                </div>

            </div>
            )
        ))

        let UploadPanel = <aside style={styles.thumbsContainer}><h4>No Uploads</h4></aside>;
        if(files && files.length >0) {
            UploadPanel = <aside style={styles.thumbsContainer}>
                <h4>Uploads</h4>
                {files}
            </aside>
        }

        let PushedUserPanel;
        if(this.props.taskProfile == 'pushed'){
            PushedUserPanel = <div>
            <Typography color="textSecondary" gutterBottom>
                <span className="task-description"> { this.props.task.userId }</span>
             </Typography>
         </div>
        }
        let TaskButtonPanel;
        if(this.props.taskProfile == 'todo') {
           TaskButtonPanel = <div>
               <IconButton aria-label="Calendar" onClick={this.onButtonCompleteTaskClicked}>
                    <CalendarIcon style={styles.taskIcon}/>
                </IconButton>
               <IconButton aria-label="Complete" onClick={this.onButtonCompleteTaskClicked}>
                    <DoneIcon style={styles.taskIcon}/>
                </IconButton>
                <IconButton aria-label="Modify">
                    <EditIcon style={styles.taskIcon} onClick={this.onButtonModifyTaskClicked}/>
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
            <div>
            {this.state.isModifyClicked ?
                <Create  authenticated={this.props.authenticated} 
                            currentUser={this.props.currentUser} 
                            stack={this.props.stack}
                            files={this.state.files}
                            taskProfile='pushed'
                            reloadTasksFunc={this.props.reloadTasksFunc}
                            reloadTaskDetail={this.reloadTask}
                            reloadPhotos={this.loadFiles}
                            task={this.props.task}/>
                        :
            <Paper>
            <Card className="task-container" style={styles.taskCard}>
                <CardActionArea >
                <CardContent>
                    {PushedUserPanel}
                    <Typography gutterBottom variant="h5" component="h2" >
                        {this.props.task.label }<span className="task-description"> { this.props.task.description }</span>
                    </Typography>
                    {/* <span className="task-label"> category:{this.props.task.category} </span> */}
                    {UploadPanel}
                </CardContent>
                </CardActionArea>
                <CardActions className="task-button-panel">
                    {TaskButtonPanel}
                </CardActions>
            </Card>
            </Paper>
            }
            </div>
        );
    }
}

export default Task