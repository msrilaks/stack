import React, { Component } from 'react';
import './Create.css';
import { createTask, uploadPhoto, modifyTask, base64toBlob, truncate,
deletePhoto, styles } from '../../util/APIUtils';
import Alert from 'react-s-alert';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import SaveIcon from '@material-ui/icons/Save';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import Dropzone from 'react-dropzone';
import Chip from '@material-ui/core/Chip';
import ChipInput from 'material-ui-chip-input'
import { styled } from '@material-ui/styles';
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
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import ListSubheader from '@material-ui/core/ListSubheader';
import Fade from '@material-ui/core/Fade';
import Zoom from '@material-ui/core/Zoom';
import Tooltip from '@material-ui/core/Tooltip';
import { ReactMultiEmail, isEmail } from 'react-multi-email';
import 'react-multi-email/style.css';
import Autocomplete from 'react-google-autocomplete';

const useStyles = makeStyles(theme => ({
    taskInputProps: {
        fontSize: '13px',
        fontFamily: "Sans Serif",
        color: '#BDBDBD',
      },
    chipContainer: {
        display: 'flex',
        float: 'right',
        maxWidth: '400px',
        paddingBottom: '20px',
        paddingLeft: '20px',
        paddingRight: '16px',
        maxRows: '2',
        flexWrap: 'wrap',
        '& > *': {
        margin: theme.spacing(0.5),
        },
    },
    emailContainer: {
        display: 'flex',
        maxRows: '2',
        flexWrap: 'wrap',
        '& > *': {
        margin: theme.spacing(0.5),
        },
    },
    chip: {
        backgroundColor:'#3f51b5',
        height: '24px',
    },
  card: {
    maxWidth: 800,
    marginBottom:35,
    marginTop:'10px',
  },
  cardContent: {
    paddingLeft:45,
    paddingRight:45,
  },
  taskTitle: {
    fontFamily: 'cursive',
    textTransform: 'capitalize',
    fontSize: 'medium',
  },
    taskButtonPanel: {
        marginLeft: 'auto',
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
    height: 0,
    paddingTop: '20%', // 16:9
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
  avatar: {
    backgroundColor: red[500],
  },
  grid: {
          display: 'flex',
          flexWrap: 'wrap',
          justifyContent: 'left',
          overflow: 'visible',
          backgroundColor: theme.palette.background.paper,
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
}));

class Create extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task:{
            id:'',
            stackId: '',
            tags: '',
            description: '',
            formattedAddress: 'Enter Task Location',
            placeId: '',
            location: {},
            userId: '',
            createdByUserId:'',
            createdDate: ''
            }
        };
        this.state = { newTaskAdded: false };
        this.state = {
            emails: [],
        };
        this.onDrop = (newfiles) => {
            newfiles: newfiles.map(file => Object.assign(file, {
                                            preview: URL.createObjectURL(file)
                                          }))
            this.setState(({ files }) => ({
             files: files.concat(newfiles)
            }))
        };
        this.state = {
            files: []
        };


        this.handleChipDelete = this.handleChipDelete.bind(this);
        this.handleChangeChips = this.handleChangeChips.bind(this);

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.removeFile = this.removeFile.bind(this);
        this.StackCreateCard = this.StackCreateCard.bind(this);
    }

    componentWillMount() {
        if(this.props.task!=null){
            this.setState(({ files }) => ({
                             files: files.concat(this.props.files)
                           }));
            this.setState({
                task:{
                    ...this.state.task,
                id:this.props.task.id,
                stackId:this.props.stack.id,
                userId:this.props.stack.userId,
                tags:this.props.task.tags,
                createdByUserId:this.props.stack.userId,
                formattedAddress:this.props.task.formattedAddress,
                description:this.props.task.description,
        }
            },function () {
                console.log(this.state.task.stackId);
            });
        } else {
            this.setState({
                task:{
                    ...this.state.task,
                stackId:this.props.stack.id,
                tags:'',
                description:'',
                createdDate:'',
                userId:this.props.stack.userId,
                createdByUserId:this.props.stack.userId
        }
            },function () {
                console.log(this.state.task.stackId);
            });
        }
       }


    removeFile(file) {
        if(file.id != null) {
        deletePhoto(this.state.task.stackId, this.state.task.id, file.id)
            .then((response) => {
                this.props.reloadPhotos();
                console.log("File deleted successfully : " + file.id);
            })
        }
        this.setState(state => {
        const index = state.files.indexOf(file);
        const files = state.files.slice(0);
        files.splice(index, 1);
        return {files};
        });
    }

    handleChipDelete(chip, index) {
        var newTagsArr = this.state.task.tags.split(',');
        newTagsArr.splice(index, 1);
        var newTags = newTagsArr.toString()
        this.setState({
                task:{
                    ...this.state.task,
                tags : newTags
                }
            },function () {
                console.log("Task tags - deleted tag:" +chip+" , tags:"+
                this.state.task.tags);
        });
    }

    handleChangeChips(chips) {
        if(this.state.task.tags && this.state.task.tags.trim!=""
        && this.state.task.tags.trim().length>0) {
             var newTags = this.state.task.tags +','+ chips +'';
             this.setState({
                                task:{
                                    ...this.state.task,
                                tags : newTags
                                }
                            },function () {
                                console.log("Task tags - add to existing" +
                                this.state.task.tags);
                        });
        } else {
            var newTags = chips +'';
            this.setState({
                    task:{
                        ...this.state.task,
                    tags : newTags
                    }
                },function () {
                    console.log("Task tags - add first and new "
                    + this.state.task.tags);
            });
        }
    }
    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;        
        let inputValue = target.value;


        this.setState({
            task:{
                ...this.state.task,
            [inputName] : inputValue
            }
        },function () {
            // console.log("### SRI " + this.state.task[inputName]+" , "+inputName);
        });        
    }


    handleSubmit(event) {
        event.preventDefault();
        const createTaskRequest = Object.assign({}, this.state.task);
        var taskPushLogEntryMap = {};
        var keyEmail=0;
        if(this.state.emails && this.state.emails!==null && this.state.emails !== undefined
            && this.state.emails.length > 0) {
            this.state.emails.forEach((email, i) => {
                taskPushLogEntryMap[keyEmail + 1] =
                    {
                        pushedUserId:  email
                    };
                    keyEmail=keyEmail+1;
            });
            createTaskRequest.taskPushLogEntryMap = taskPushLogEntryMap;
        }
        console.log(createTaskRequest);
        console.log(this.props.taskProfile);
        if(this.props.taskProfile == 'pushed') {

            modifyTask(createTaskRequest)
            .then((response) => {
                this.state.files.forEach((file, i) => {
                        console.log("SRI File : " + file.id +"," +file.name);
                            if(file.id == null) {
                                uploadPhoto(this.state.task.stackId, this.state.task.id,
                                 file.name, file).then(response => {
                                  this.props.reloadPhotos();
                                  console.log("File uploaded successfully!");
                                 });
                            } else {
                                console.log("File already uploaded : " + file.id);
                            }
                })
                Alert.success("Task modified successfully!");
                this.props.reloadTaskDetail();
                this.props.reloadTasksFunc();

            }).catch(error => {
                Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
            });
        } else {
        createTask(createTaskRequest)
        .then(response => {
                this.setState({
                    task: response,
                });
                this.state.files.forEach((file, i) => {
                uploadPhoto(this.state.task.stackId, this.state.task.id, file.name, file);
            })
            Alert.success("Task created successfully!");
            this.props.reloadTasksFunc();
        }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }
    }

    StackCreateCard() {
        const classes = useStyles();
        const [expanded, setExpanded] = React.useState(false);

        const handleExpandClick = () => {
            setExpanded(!expanded);
        };

        const StackChipInput = styled(ChipInput)({
            '& input': {
                fontSize: '15px',
                fontFamily: "Sans Serif",
                paddingBottom: '0px',
            },
            '& span': {
                color: '#ffffff',
                fontSize: '13px',
                fontWeight: '300',
            },
            '& svg': {
                color: '#ffffff',
                height: '18px',
            }
        });

    const StyledDiv = styled('div')({
        fontSize: '15px',
        fontFamily: "Sans Serif",
        padding: '0',
        marginTop: '30px',
    });

    const StyledSection = styled('section')({
        padding: '0',
    });

    let UploadPanel  =  (
                <div className={classes.grid}>
                    <GridList cellHeight={'auto'} className={classes
                    .gridList} cols={2}>
                        <GridListTile key="dropzone" cols={2} style={{ height:
                                                'auto', paddingBottom:'20px',borderRadius: '.2rem'}}>
                            <Dropzone onDrop={this.onDrop}>
                                {({getRootProps, getInputProps}) => (
                                <StyledSection className="container">
                                    <StyledDiv {...getRootProps({className: 'dropzone'})}>
                                        <input {...getInputProps()} />
                                        <p>Drag 'n' drop, or click here to
                                        upload files</p>
                                    </StyledDiv>
                                </StyledSection>
                                )}
                            </Dropzone>
                        </GridListTile>
                        {this.state.files.map(file =>(
                            <GridListTile key={file.id} style={{ padding: '2px' }}
                            cols={1}>
                                <img src={file.preview}/>
                                <GridListTileBar
                                    title={file.title}
                                    classes={{
                                        root: classes.titleBar,
                                        title: classes.title,
                                    }}
                                    actionIcon={
                                            <IconButton aria-label={`download ${file.id}`}
                                             onClick={() => this.removeFile
                                             (file)}
                                            className={classes.gridIcon}>
                                                <DeleteIcon />
                                            </IconButton>
                                    }
                                />
                            </GridListTile>
                        )
                    )}
                    </GridList>
                </div>
            )

        let defaultTags=[];
        if(this.state.task.tags && this.state.task.tags.trim() !=""
            && this.state.task.tags.trim().length > 0
            && this.state.task.tags.split(',').length>0 ){
                defaultTags = this.state.task.tags.split(',')
        }


        return (
        <Zoom timeout={150} in={this.state.task}>
        <Card className={classes.card} elevation='15'>
            <CardHeader className={classes.cardHeader}
                avatar={
                    <Avatar aria-label="task"
                        className={classes.avatar}
                        src={this.props.currentUser.imageUrl}
                        alt={this.props.currentUser.name}>
                    </Avatar>
                }

                title={
                    <Typography variant="h6"
                    color="textPrimary" component="p" className={classes.taskTitle}>
                        {<span style={{overflow: 'hidden', textOverflow:
                        'ellipsis'}}>
                            {truncate(this.state.task.description)}
                        </span>}
                    </Typography>}

                subheader={
                     <Typography variant="caption"
                        color="textPrimary" component="p">
                                {this.state.task.createdDate}
                    </Typography>
                }>
            </CardHeader>
        <CardContent className={classes.cardContent}>

        <div className={classes.chipContainer}>
            <StackChipInput
                value={defaultTags}
                placeholder="#tags"
                alwaysShowPlaceholder='true'
                onAdd={(chips) => this.handleChangeChips(chips)}
                onDelete={(chips, index) => this.handleChipDelete(chips,
                    index)}
                inputProps={{
                    'allowDuplicates': 'false',
                }}
                inputLabelProps={{
                    'font': 'sans-serif',
                    'fontFamily': 'sans-serif'
                }}
                classes={{
                    chip: classes.chip,
                }}
            />
            </div>



            <Typography variant="body1" color="textSecondary" component="p"
            style={{width: '100%',
                                           marginTop: '30px',
                                               marginLeft: '0px',
                                               marginRight: '0px',
                                               marginBottom: '0px',}}
            className={classes.taskInputProps}>
                    <TextField
                    id="standard-multiline-flexible"
                    style={{marginBottom: '0px'}}
                    inputProps={{style: {fontSize: '15px',
                                                 fontFamily: "Sans Serif",}}} // font size of input text
                    InputLabelProps={{style: {fontSize: '15px',
                                                      fontFamily: "Sans Serif",
                                                      color: '#BDBDBD',}}} // font size of input label
                    label="description"
                    multiline
                    fullWidth
                    variant="outlined"
                    rowsMax="4"
                    name="description"
                    value={this.state.task.description} onChange={this.handleInputChange} required
                    margin="normal"
                    />
            </Typography>
             <div>
             <Typography variant="body1" color="textSecondary" component="p"
                         style={{width: '100%',
                               marginTop: '30px',
                                   marginLeft: '0px',
                                   marginRight: '0px',
                                   marginBottom: '0px',}}
                         className={classes.taskInputProps}>
             <Autocomplete
                 style={{width: '100%',height: '38px',border: '1px solid #dcdcdc',borderRadius: '.2rem',
                 padding: '14px'}}
                 placeholder={this.state.task.formattedAddress}
                 InputProps={{
                         className: classes.taskInputProps
                       }}
                 InputLabelProps={{style: {fontSize: '15px',
                       fontFamily: "Sans Serif",
                       color: '#BDBDBD',}}} // font size of input label
                 onPlaceSelected={(place) => {
                 console.log(place);
                 this.setState({
                                     task:{
                                         ...this.state.task,
                                     placeId: place.place_id,
                                     formattedAddress: place.formatted_address,
                                     location: place.geometry.location
                                     }
                                 },function () {
                                     console.log("Location "
                                     + this.state.task.location);
                             });
                 }}
                 types={['establishment']}
             />
             </Typography>
             </div>
            <div className={classes.emailContainer}>
            <Typography variant="body1" color="textSecondary" component="p"
                                     style={{width: '100%',
                                      marginTop: '30px',
                                          marginLeft: '0px',
                                          marginRight: '0px',
                                          marginBottom: '0px',}}
                                     className={classes.taskInputProps}>
             <ReactMultiEmail
                       placeholder="push to @emails"
                       style={{margin: '0px',color: '#bdbdbd',border: '1px solid #dcdcdc',borderRadius: '.2rem',width: '100%',
                       height: '38px'}}
                       InputLabelProps={{style: {fontSize: '15px',
                             fontFamily: "Sans Serif",
                             color: '#BDBDBD',}}} // font size of input label
                       emails={this.state.emails}
                       onChange={(_emails: string[]) => {
                         this.setState({ emails: _emails });
                       }}
                       validateEmail={email => {
                         return isEmail(email); // return boolean
                       }}
                       getLabel={(
                         email: string,
                         index: number,
                         removeEmail: (index: number) => void,
                       ) => {
                         return (
                           <div data-tag key={index}>
                             {email}
                             <span data-tag-handle onClick={() => removeEmail(index)}>
                               ×
                             </span>
                           </div>
                         );
                       }}
                     />
</Typography>
            </div>

            {UploadPanel}
        </CardContent>
        <CardActions disableSpacing>
        <div className={classes.taskButtonPanel}>
            <Tooltip title="Save" placement="bottom">
                <IconButton type="submit" aria-label="Submit" >
                    <SaveIcon/>
                </IconButton>
            </Tooltip>
        </div>
            {/*<IconButton
                className={clsx(classes.expand, {
                [classes.expandOpen]: expanded,
                })}
                onClick={handleExpandClick}
                aria-expanded={expanded}
                aria-label="show more">
                <ExpandMoreIcon />
            </IconButton>*/}
        </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent>
                </CardContent>
            </Collapse>
        </Card>
        </Zoom>
        );
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <this.StackCreateCard/>
            </form>
        );
    }
}

export default Create