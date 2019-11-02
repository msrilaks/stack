import React, { Component } from 'react';
import './Task.css';
import Create from '../create/Create';
import Profile from '../../user/profile/Profile';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Alert from 'react-s-alert';
import { deleteTask, patchTask, getPhotos, base64toBlob,
    truncate, addEvent, styles, getUser } from '../../util/APIUtils';
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
import FavoriteIcon from '@material-ui/icons/Favorite';
import ShareIcon from '@material-ui/icons/Share';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import Chip from '@material-ui/core/Chip';
import Tooltip from '@material-ui/core/Tooltip';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import Zoom from '@material-ui/core/Zoom';
import TextField from '@material-ui/core/TextField';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import ListSubheader from '@material-ui/core/ListSubheader';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import 'date-fns';
import { format } from 'date-fns'
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

const StyledMenu = withStyles({
  paper: {
    border: '1px solid #d3d4d5',
  },
})(props => (
  <Menu
    elevation={5}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'center',
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'center',
    }}
    {...props}
  />
));

const StyledMenuItem = withStyles(theme => ({
  root: {
    '&:focus': {
      backgroundColor: theme.palette.primary.main,
      '& .MuiListItemIcon-root, & .MuiListItemText-primary': {
        color: theme.palette.common.white,
      },
    },
  },
}))(MenuItem);

const useStyles = makeStyles(theme => ({
  card: {
    maxWidth: 800,
    marginBottom:35,
  },
  cardHeader:{
    backgroundColor: 'aliceblue',
    borderBottom: 'aliceblue',
    borderBottomWidth: '1px',
    borderBottomStyle: 'groove',
    paddingTop: '8px',
    paddingBottom: '8px',
  },
    media: {
      height: 140,
    },
  taskTitle: {
    fontFamily: 'cursive',
    textTransform: 'capitalize',
    fontSize: 'medium',
  },
  taskDetail: {
    fontSize: 'medium',
    fontWeight: '500',
  },
  taskButtonPanel: {
      marginLeft: 'auto',
  },
  expand: {
    transform: 'rotate(0deg)',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: 'rotate(180deg)',
  },
  chipContainer: {
        display: 'flex',
        float: 'right',
        paddingRight: '20px',
        flexWrap: 'wrap',
        '& > *': {
        margin: theme.spacing(0.5),
        },
    },
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
  },
    modalPaper: {
      backgroundColor: theme.palette.background.paper,
      boxShadow: theme.shadows[5],
      padding: theme.spacing(2, 4, 3),
    },
    grid: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'left',
        overflow: 'visible',
        backgroundColor: theme.palette.background.paper,
    },
    gridEvent: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'left',
        overflow: 'visible',
        backgroundColor: theme.palette.background.paper,
        width: '600px',
    },

    gridList: {
        width: '100%',
        height: 'auto',
    },
    gridIcon: {
        color: 'white',
    },
    title: {
      color: theme.palette.primary,
    },
    titleBar: {
       background:
            'linear-gradient(to top, rgba(0,0,0,0.5) 0%, ' +
            'rgba(0,0,0,0.2) 70%, rgba(0,0,0,0) 100%)',
    },
    pushButton: {
        margin: theme.spacing(1),
        float: 'right',
      },
      calendar: {
          width: '60%',
        },
}));



class Task extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isModifyClicked: false,
            pushUserId: '',
        }
        this.state = {
             files: [],
             event:{
                 location:'',
                 start:new Date(),
                 end:new Date(),
             },
             pushUser:{
                 imageUrl:'',
                 name:'',
                 email:'',
             }
        };


        this.onButtonDeleteTaskClicked = this.onButtonDeleteTaskClicked.bind(this);
        this.onButtonCalendarClicked = this.onButtonCalendarClicked.bind(this);
        this.onButtonCompleteTaskClicked = this.onButtonCompleteTaskClicked.bind(this);
        this.onButtonTodoTaskClicked = this.onButtonTodoTaskClicked.bind(this);
        this.onButtonModifyTaskClicked = this.onButtonModifyTaskClicked.bind(this);
        this.onButtonPushTaskClicked = this.onButtonPushTaskClicked.bind(this);
        this.onButtonAddEventClicked = this.onButtonAddEventClicked.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.reloadTask = this.reloadTask.bind(this);
        this.loadFiles = this.loadFiles.bind(this);
        this.tagChips = this.tagChips.bind(this);
        this.StackTaskCard = this.StackTaskCard.bind(this);
        this.PushModal = this.PushModal.bind(this);
        this.EventModal = this.EventModal.bind(this);
        this.OptionsMenu = this.OptionsMenu.bind(this);
        this.loadUserAvatar=this.loadUserAvatar.bind(this);
    }
    componentDidMount() {
        this.setState({
            isModifyClicked: false,
            pushUserId:this.props.task.userId,
            pushModalOpen:false,
            eventModalOpen:false,
        });

        this.setState({
            event:{
                location:'',
                start:new Date(),
                end:new Date(),
            },
            pushUser:{
                imageUrl:'',
                name:'',
                email:'',
            }
        },function () {
            console.log("location "+this.state.event.location);
            console.log("eventStartDate "+this.state.event.start);
            console.log("eventEndDate "+this.state.event.end);
        });

        this.loadFiles();
        this.loadUserAvatar();
    }

    loadUserAvatar() {
         getUser(this.props.task)
                 .then(response => {
                     this.setState({
                          pushUser:{
                               ...this.state.pushUser,
                           name : response.name,
                           email: response.email,
                           imageUrl: response.imageUrl,
                           }
                     },function () {
                         console.log("pushUser" + this.state
                         .pushUser.name);
                     });
                 }).catch(error => {
                 });
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

    onButtonAddEventClicked(addevent){
        const eventRequest = Object.assign({}, this.state.event);
        console.log(eventRequest);
        addEvent(eventRequest, this.props.stack.id, this.props.task.id)
            .then((response) => {
                this.setState({
                        eventModalOpen:false
                    },function () {
                        console.log("AddEventRequest "+response);
                    });
                Alert.success("Google Calendar Event added.");
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

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        let inputValue = target.value;
        this.setState({
            pushUserId:inputValue
        },function () {
            console.log("### SRI " + this.state.pushUserId);
        });
    }

        onButtonPushTaskClicked(event) {
            const pushTaskRequest = Object.assign({}, this.props.task);
            pushTaskRequest.userId = this.state.pushUserId;
            console.log(pushTaskRequest);
            patchTask(pushTaskRequest,"isPushed=true")
            .then((response) => {
                this.setState({
                    },function () {
                        console.log("PatchTaskRequest "+this.state.task);
                    });
                Alert.success("Task pushed out of your stack successfully!");
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


    onButtonCalendarClicked() {
        this.setState({
            eventModalOpen: !this.state.eventModalOpen,
        },function () {
            console.log("eventModalOpen "+this.state.eventModalOpen);
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
                                size="small"
                                clickable={true}
                                onClick={()=>this.props.setFilterTags(data)}
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

        let UploadPanel;
        if(prevfiles && prevfiles.length>0){ UploadPanel= (
            <div className={classes.grid}>
                <GridList cellHeight={'auto'} className={classes
                .gridList} cols={4}>
                    {Object.entries(prevfiles).map(([key, file])=>(
                        <GridListTile key={file.id} style={{ padding: '2px' }}
                        cols={1}>
                            <img src={file.preview} alt={file.title}/>
                            <GridListTileBar
                                title={file.title}
                                classes={{
                                                root: classes.titleBar,
                                                title: classes.title,
                                              }}
                                actionIcon={
                                    <a href={file.preview} download={file.title} >
                                        <IconButton aria-label={`download ${file.id}`}
                                        className={classes.gridIcon}>
                                            <CloudDownloadIcon />
                                        </IconButton>
                                    </a>
                                }
                            />
                        </GridListTile>
                    )
                )}
                </GridList>
            </div>
        )}

        let PushedUserPanel;
        if(this.props.taskProfile == 'pushed'){
            PushedUserPanel = <div>
            <Typography color="textSecondary" gutterBottom>
                <span className="task-description"> { this.props.task.userId }</span>
             </Typography>
         </div>
        }

        let StackUserAvatar;
        if(this.props.task.pushedUserId == '' ||
            this.props.task.pushedUserId == null) {
                 StackUserAvatar = <Profile
                        authenticated={this.props.authenticated}
                        currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        imageUrl={this.props.currentUser.imageUrl}
                        name={this.props.currentUser.name}
                        email={this.props.currentUser.email}/>
        } else {
            if(this.state.pushUser.imageUrl == '') {
                StackUserAvatar = <Profile
                                    authenticated={this.props.authenticated}
                                    currentUser={this.props.currentUser}
                                    stack={this.props.stack}
                                    imageUrl=''
                                    name=''
                                    email={this.props.task.pushedUserId}/>
            } else {
                StackUserAvatar = <Profile
                                        authenticated={this.props.authenticated}
                                        currentUser={this.props.currentUser}
                                        stack={this.props.stack}
                                        imageUrl={this.state.pushUser.imageUrl}
                                        name={this.state.pushUser.name}
                                        email={this.state.pushUser.email}/>
            }
        }

        let TaskButtonPanel;
        if(this.props.taskProfile == 'todo') {
           TaskButtonPanel = <div className={classes.taskButtonPanel}>
                <Tooltip title="Mark as Completed" placement="bottom">
                   <IconButton aria-label="Complete" onClick={this.onButtonCompleteTaskClicked}>
                        <DoneIcon/>
                    </IconButton>
                </Tooltip>
                <Tooltip title="Modify" placement="bottom">
                    <IconButton aria-label="Modify">
                        <EditIcon onClick={this.onButtonModifyTaskClicked}/>
                    </IconButton>
                </Tooltip>
                <Tooltip title="Delete" placement="bottom">
                    <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                        <DeleteIcon/>
                    </IconButton>
                </Tooltip>
                <Tooltip title="Add to Google Calendar" placement="bottom">
                    <IconButton aria-label="Calendar" onClick={this.onButtonCalendarClicked}>
                        <CalendarIcon/>
                    </IconButton>
                </Tooltip>
            </div>
        } else if(this.props.taskProfile == 'deleted'){
            TaskButtonPanel = <div className={classes.taskButtonPanel}>
         </div>
        } else if(this.props.taskProfile == 'pushed'){
            TaskButtonPanel = <div className={classes.taskButtonPanel}>
            <Tooltip title="Nudge" placement="bottom">
                <IconButton aria-label="Remind">
                    <AlarmIcon />
                </IconButton>
            </Tooltip>
            <Tooltip title="Delete" placement="bottom">
                <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                    <DeleteIcon />
                </IconButton>
            </Tooltip>
         </div>
        }else if(this.props.taskProfile == 'completed'){
            TaskButtonPanel = <div className={classes.taskButtonPanel}>
            <Tooltip title="Mark as TODO" placement="bottom">
                <IconButton aria-label="Undo" onClick={this.onButtonTodoTaskClicked}>
                     <UndoIcon />
                 </IconButton>
            </Tooltip>
            <Tooltip title="Delete" placement="bottom">
                 <IconButton aria-label="Delete" onClick={this.onButtonDeleteTaskClicked}>
                        <DeleteIcon/>
                </IconButton>
           </Tooltip>
         </div>
        }


        return (
            <Zoom timeout={150} in={this.props.task}>
            <Card className={classes.card} elevation='10'>
                <CardHeader className={classes.cardHeader}
                    avatar={StackUserAvatar}
                    action={
                        <div>
                        <this.OptionsMenu/>
                        <this.PushModal/>
                        <this.EventModal/>
                        </div>
                    }

                    title={
                        <Typography variant="h6"
                        color="textPrimary" component="p" className={classes.taskTitle}>
                            {<span style={{overflow: 'hidden', textOverflow:
                            'ellipsis'}}>
                                {truncate(this.props.task.description)}
                            </span>}
                        </Typography>}

                    subheader={
                         <Typography variant="caption"
                            color="textPrimary" component="p">
                            {
                                format(new Date(this.props.task.createdDate)
                                , 'HH:mm a, dd MMM yyyy')}
                        </Typography>
                    }>
                    </CardHeader>

                    <CardContent>
                        <GridList cellHeight={'auto'} className={classes
                             .gridList} cols={2}>
                            <GridListTile key='taskDetail'
                                style={{ padding:'2px' }} cols={2}>
                                     <this.tagChips/>
                            </GridListTile>
                            <GridListTile key='taskDetail2'
                                  style={{ padding:'2px' }} cols={2}>
                                  <Typography variant="subtitle2"
                                  color="textPrimary"
                                       component="p" className={classes.taskDetail}>
                                      { this.props.task.description }
                                  </Typography>
                            </GridListTile>
                        </GridList>
                    </CardContent>
                    <CardContent>
                        <GridList cellHeight={'auto'} className={classes
                                                     .gridList} cols={2}>
                            <GridListTile key='taskDetail2'
                                  style={{ padding:'2px' }} cols={2}>
                                  {UploadPanel}
                             </GridListTile>
                        </GridList>
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
                    <CardContent >

                    </CardContent>
                </Collapse>
            </Card>
            </Zoom>
        );
    }

 OptionsMenu() {
 const classes = useStyles();
 const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = event => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  const handlePush = () => {
        handleClose();
        this.setState({
              pushModalOpen: !this.state.pushModalOpen,
          },function () {
              console.log("pushModalOpen "+this.state.pushModalOpen);
          });
      };

  const handleEvent = () => {
    handleClose();
    this.setState({
          eventModalOpen: !this.state.eventModalOpen,
      },function () {
          console.log("eventModalOpen "+this.state.eventModalOpen);
      });
  };
    if(this.props.taskProfile !== 'todo') {
        return(<div/>);
    }
 return(
   <div>
       <Tooltip title="Options" placement="bottom">
                       <IconButton aria-label="Options" aria-haspopup="true" onClick={handleClick}>
                            <MoreVertIcon/>
                        </IconButton>
                    </Tooltip>
       <StyledMenu
         id="simple-menu"
         anchorEl={anchorEl}
         keepMounted
         open={Boolean(anchorEl)}
         onClose={handleClose}
       >
         <StyledMenuItem onClick={handlePush}>
         <ListItemIcon>
                     <ShareIcon fontSize="small" />
                   </ListItemIcon>
                   <ListItemText primary="Push" />
         </StyledMenuItem>
          {/*<StyledMenuItem onClick={handleEvent}>
          <ListItemIcon>
                      <CalendarIcon fontSize="small" />
                    </ListItemIcon>
                    <ListItemText primary="Google Calendar Event" />
          </StyledMenuItem>*/}
       </StyledMenu>
     </div>
 );
 }

 PushModal() {
    const classes = useStyles();
      return (
        <div>
          <Modal
            aria-labelledby="transition-modal-title"
            aria-describedby="transition-modal-description"
            className={classes.modal}
            open={this.state.pushModalOpen}
            onClose={()=>{this.setState({pushModalOpen:false})}}
            closeAfterTransition
            BackdropComponent={Backdrop}
            BackdropProps={{
              timeout: 500,
            }}
          >
            <Fade in={this.state.pushModalOpen}>
              <div className={classes.modalPaper}>
                            <TextField
                            id="email-input"
                            label="push to"
                            type="email"
                            name="pushUserId"
                            fullWidth
                            defaultValue={this.state.pushUserId}
                            autoComplete="email"
                            margin="normal"
                            onChange={this.handleInputChange} required
                />
                <Button variant="contained" color="primary"
                className={classes.pushButton}
                onClick={this.onButtonPushTaskClicked}>
                     Push
                 </Button>
              </div>
            </Fade>
          </Modal>

        </div>
      );
    }

 EventModal() {
    const classes = useStyles();

    const handleLocationChange = event => {
        const target = event.target;
        const inputName = target.name;
        let inputValue = target.value;
        this.setState({
            event:{
                ...this.state.event,
                location:inputValue,
            },
        },function () {
            console.log("location "+this.state.event.location);
        });
    };
    const handleStartDateChange = date => {
        this.setState({
            event:{
                ...this.state.event,
                start:date,
            },
        },function () {
            console.log("eventStartDate "+this.state.event.start);
        });
    };
    const handleEndDateChange = date => {
        this.setState({
            event:{
                ...this.state.event,
                end:date,
            },
        },function () {
            console.log("eventEndDate "+this.state.event.end);
        });
    };
        return (
         <div>
                  <Modal
                    aria-labelledby="transition-modal-title"
                    aria-describedby="transition-modal-description"
                    className={classes.modal}
                    open={this.state.eventModalOpen}
                    onClose={()=>{this.setState({eventModalOpen:false})}}
                    closeAfterTransition
                    BackdropComponent={Backdrop}
                    BackdropProps={{
                      timeout: 500,
                    }}
                  >
            <Fade in={this.state.eventModalOpen}>
            <div className={classes.gridEvent}>
            <div className={classes.modalPaper}>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <GridList cellHeight={'auto'} className={classes
                        .gridList} cols={2}>
                            <GridListTile key='dateStart'
                                style={{ padding:'2px' }} cols={1}>
                                <KeyboardDatePicker
                                    margin="normal"
                                    id="mui-pickers-date"
                                    label="Start Date"
                                    value={this.state.event.start}
                                    onChange={handleStartDateChange}
                                    KeyboardButtonProps={{
                                    'aria-label': 'Start date',
                                }}
                            />
                            </GridListTile>
                            <GridListTile key='timeStart'
                                style={{ padding:'2px' }} cols={1}>
                                    <KeyboardTimePicker
                                    margin="normal"
                                    id="mui-pickers-time"
                                    label="Start time"
                                    value={this.state.event.start}
                                    onChange={handleStartDateChange}
                                    KeyboardButtonProps={{
                                    'aria-label': 'Start Time',
                                }}
                            />
                            </GridListTile>
                            <GridListTile key='dateEnd'
                                style={{ padding:'2px' }} cols={1}>
                                <KeyboardDatePicker
                                    margin="normal"
                                    id="mui-pickers-date"
                                    label="End Date"
                                    value={this.state.event.end}
                                    onChange={handleEndDateChange}
                                    KeyboardButtonProps={{
                                    'aria-label': 'End date',
                                }}
                            />
                            </GridListTile>
                            <GridListTile key='timeEnd'
                                style={{ padding:'2px' }} cols={1}>
                                    <KeyboardTimePicker
                                    margin="normal"
                                    id="mui-pickers-time"
                                    label="End time"
                                    value={this.state.event.end}
                                    onChange={handleEndDateChange}
                                    KeyboardButtonProps={{
                                    'aria-label': 'End Time',
                                }}
                            />
                            </GridListTile>
                        </GridList>
                </MuiPickersUtilsProvider>
                <TextField
                            id="location"
                            label="location"
                            name="location"
                            fullWidth
                            margin="normal"
                            onChange={handleLocationChange}
                />
                <Button variant="contained" color="primary"
                    className={classes.pushButton}
                    onClick={this.onButtonAddEventClicked}>Add</Button>
                </div>
                </div>
            </Fade>
            </Modal>
            </div>

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