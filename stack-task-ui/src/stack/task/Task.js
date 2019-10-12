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
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import CardHeader from '@material-ui/core/CardHeader';
import Collapse from '@material-ui/core/Collapse';
import Avatar from '@material-ui/core/Avatar';
import { red } from '@material-ui/core/colors';
import FavoriteIcon from '@material-ui/icons/Favorite';
import ShareIcon from '@material-ui/icons/Share';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import Chip from '@material-ui/core/Chip';

const useStyles = makeStyles(theme => ({
  card: {
    maxWidth: 600,
    marginBottom:30,
  },
  media: {
    height: 0,
    paddingTop: '20%', // 16:9
  },
  expand: {
    transform: 'rotate(0deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: 'rotate(180deg)',
  },
  avatar: {
    backgroundColor: red[500],
  },
  chipContainer: {
          display: 'flex',
          justifyContent: 'left',
          flexWrap: 'wrap',
          '& > *': {
            margin: theme.spacing(0.5),
          },
        },
}));



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
        this.truncate = this.truncate.bind(this);
        this.tagChips = this.tagChips.bind(this);
        this.StackTaskCard = this.StackTaskCard.bind(this);
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

    truncate(str) {
        return str.length > 125 ? str.substring(0, 125) + "..." : str;
    }
    tagChips() {
    const classes = useStyles();
            return <div className={classes.chipContainer}>
                    {
                        this.props.task.tags
                        && this.props.task.tags.trim() !== ""
                        && this.props.task.tags.length>0
                        && this.props.task.tags.split(',').map(data => {
                        return (
                            <Chip
                                key={data}
                                label={data}
                                color="primary"/>
                        );
                    })}
                </div>
    }
    StackTaskCard() {
        const classes = useStyles();
        const [expanded, setExpanded] = React.useState(false);

        const handleExpandClick = () => {
            setExpanded(!expanded);
        };

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

        let UploadPanel = <aside style={styles.thumbsContainer}></aside>;
        if(files && files.length >0) {
            UploadPanel = <aside style={styles.thumbsContainer}>
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
                    <CalendarIcon/>
                </IconButton>
               <IconButton aria-label="Complete" onClick={this.onButtonCompleteTaskClicked}>
                    <DoneIcon/>
                </IconButton>
                <IconButton aria-label="Modify">
                    <EditIcon onClick={this.onButtonModifyTaskClicked}/>
                </IconButton>
                <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                    <DeleteIcon/>
                </IconButton>
            </div>
        } else if(this.props.taskProfile == 'deleted'){
            TaskButtonPanel = <div>
            <IconButton aria-label="Clear">
                 <ClearIcon/>
             </IconButton>
         </div>
        } else if(this.props.taskProfile == 'pushed'){
            TaskButtonPanel = <div>
            <IconButton aria-label="Remind">
                <AlarmIcon />
            </IconButton>
            <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                <DeleteIcon />
            </IconButton>
         </div>
        }else if(this.props.taskProfile == 'completed'){
            TaskButtonPanel = <div>
            <IconButton aria-label="Undo" onClick={this.onButtonTodoTaskClicked}>
                 <UndoIcon />
             </IconButton>
             <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                    <DeleteIcon/>
            </IconButton>
         </div>
        }


        return (
            <Card className={classes.card}>
                <CardHeader
                    avatar={
                        <Avatar aria-label="task"
                            className={classes.avatar}
                            src={this.props.currentUser.imageUrl}
                            alt={this.props.currentUser.name}>
                        </Avatar>
                    }
                    action={
                        <IconButton aria-label="settings">
                            <MoreVertIcon />
                        </IconButton>
                    }

                    title={<span style={{overflow: 'hidden', textOverflow:
                    'ellipsis'}}>
                                                  {this.truncate(this.props.task.description)}
                                              </span>}
                    subheader={this.props.task.createdDate}>

                    </CardHeader>
                    <CardContent>
                    <this.tagChips/>
                    </CardContent>
                <CardMedia
                    className={classes.media} >
                </CardMedia>
                <CardContent>

                    <Typography variant="body2" color="textSecondary" component="p">
                        { this.props.task.description }
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>
                    {TaskButtonPanel}
                    <IconButton
                        className={clsx(classes.expand, {
                            [classes.expandOpen]: expanded,
                            })}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more">
                        <ExpandMoreIcon />
                    </IconButton>
                </CardActions>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <Typography paragraph>Uploads</Typography>
                        {UploadPanel}
                    </CardContent>
                </Collapse>
            </Card>
        );
    }



    render() {
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
            <div>
            <this.StackTaskCard/>
            </div>
            }
            </div>
        );
    }
}

export default Task