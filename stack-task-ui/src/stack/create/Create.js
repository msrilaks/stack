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

const useStyles = makeStyles(theme => ({
  card: {
    maxWidth: 800,
    marginBottom:35,
  },
  taskTitle: {
    fontFamily: 'cursive',
    textTransform: 'capitalize',
  },
  taskDetail: {
    fontSize: '1rem',
  },
  cardHeader:{
    backgroundColor: 'aliceblue',
    borderBottom: 'aliceblue',
    borderBottomWidth: '1px',
    borderBottomStyle: 'groove',
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
            userId: '',
            createdByUserId:'',
            createdDate: ''
            }
        };
        this.state = { newTaskAdded: false };
        this.onDrop = (newfiles) => {

              console.log("##SRI " + newfiles)
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
            console.log("### SRI " + this.state.task[inputName]+" , "+inputName);
        });        
    }

    handleSubmit(event) {
        event.preventDefault();   

        const createTaskRequest = Object.assign({}, this.state.task);
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
            },
            '& span': {
                color: '#1a9099',
                fontWeight: '300',
            }
        });


    let UploadPanel  =  (
                <div className={classes.grid}>
                    <GridList cellHeight={'auto'} className={classes
                    .gridList} cols={4}>
                        <GridListTile key="dropzone" cols={4} style={{ height:
                                                'auto', paddingBottom:'20px'}}>
                            <Dropzone onDrop={this.onDrop}>
                                {({getRootProps, getInputProps}) => (
                                <section className="container">
                                    <div {...getRootProps({className: 'dropzone'})}>
                                        <input {...getInputProps()} />
                                        <p>Drag 'n' drop, or click here to
                                        upload files</p>
                                    </div>
                                </section>
                                )}
                            </Dropzone>
                        </GridListTile>
                        {this.state.files.map(file =>(
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
        <Card className={classes.card} elevation='10'>
            <CardHeader className={classes.cardHeader}
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
        <CardContent>
        <div style={styles.tagContainer}>
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
            />
            </div>
        </CardContent>
        <CardContent>
            <TextField
            id="email-input"
            label="push to"
            type="email"
            name="userId"
            defaultValue={this.state.task.userId}
            autoComplete="email"
            margin="normal"
            variant="outlined"
            style={{ paddingLeft: 16}}
            value={this.state.task.userId} onChange={this.handleInputChange} required
            />

            <Typography variant="body1" color="textSecondary" component="p"
            style={{ paddingLeft: 16, paddingRight: 16}}
            className={classes.taskDetail}>
                    <TextField
                    id="standard-multiline-flexible"
                    label="description"
                    variant="outlined"
                    multiline
                    fullWidth
                    rowsMax="4"
                    name="description"
                    value={this.state.task.description} onChange={this.handleInputChange} required
                    margin="normal"
                    />
            </Typography>
        </CardContent>

        <CardContent>
            {UploadPanel}
        </CardContent>
        <CardActions disableSpacing>
            <IconButton type="submit" aria-label="Submit">
                <SaveIcon/>
            </IconButton>
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