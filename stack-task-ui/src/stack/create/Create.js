import React, { Component } from 'react';
import './Create.css';
import { createTask, modifyTask, styles } from '../../util/APIUtils';
import Alert from 'react-s-alert';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import IconButton from '@material-ui/core/IconButton';
import SaveIcon from '@material-ui/icons/Save';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import Dropzone from 'react-dropzone'

const thumbsContainer = {
  display: 'flex',
  flexDirection: 'row',
  flexWrap: 'wrap',
  marginTop: 16
}
const thumb = {
  display: 'inline-flex',
  borderRadius: 2,
  border: '1px solid #eaeaea',
  marginBottom: 8,
  marginRight: 8,
  width: 100,
  height: 100,
  padding: 4,
  boxSizing: 'border-box'
}
const thumbInner = {
  display: 'flex',
  minWidth: 0,
  overflow: 'hidden'
}
const img = {
  display: 'block',
  width: 'auto',
  height: '100%'
}

class Create extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task:{
            id:'',
            stackId: '',
            category: '',
            label: '',
            description: '',
            userId: '',
            createdByUserId:''
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


        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount() {

        if(this.props.task!=null){
            this.setState({
                task:{
                    ...this.state.task,
                id:this.props.task.id,
                stackId:this.props.stack.id,
                userId:this.props.stack.userId,
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
                userId:this.props.stack.userId,
                createdByUserId:this.props.stack.userId
        }
            },function () {
                console.log(this.state.task.stackId);
            });
        }
       }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;        
        const inputValue = target.value;

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
                Alert.success("Task modified successfully!");
                 this.props.reloadTasksFunc();
                 this.props.reloadTaskDetail();
            }).catch(error => {
                Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
            });
        } else {
        createTask(createTaskRequest)
        .then(response => {
            Alert.success("Task created successfully!");
            this.props.reloadTasksFunc();
        }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }
    }

    render() {

        const files = this.state.files.map(file => (
            <div style={thumb} key={file.name}>
            <div style={thumbInner}>
              <img
                src={file.preview}
                style={img}
              />
            </div>
            </div>
        ))
        return (
            <form onSubmit={this.handleSubmit}>
            <Card className="create-container" style={styles.taskCard}>
            <CardActionArea>
              <CardContent>
                {/* <TextField
                    id="standard-name"
                    label="label"
                    name="label"
                    style={styles.taskTextField}
                    value={this.state.task.label} onChange={this.handleInputChange} required
                    margin="normal"
                /> */}
                <TextField
                    id="standard-required"
                    label="category"
                    name="category"
                    defaultValue="default"
                    style={styles.taskTextField}
                    value={this.state.task.category} onChange={this.handleInputChange} required
                    margin="normal"
                />
                <Typography variant="body2" component="p">
                    <TextField
                        id="standard-multiline-flexible"
                        label="details"
                        multiline
                        fullWidth
                        rowsMax="4"
                        name="description"
                        value={this.state.task.description} onChange={this.handleInputChange} required
                        margin="normal"
                    />
                </Typography>
                <TextField
                    id="email-input"
                    label="push to"
                    type="email"
                    name="userId"
                    fullWidth
                    defaultValue={this.props.currentUser.email}
                    autoComplete="email"
                    margin="normal"
                    value={this.state.task.userId} onChange={this.handleInputChange} required
                />

            <Dropzone onDrop={this.onDrop}>
                {({getRootProps, getInputProps}) => (
                    <section className="container">
                    <div {...getRootProps({className: 'dropzone'})}>
                      <input {...getInputProps()} />
                      <p>Drag 'n' drop some files here, or click to select files</p>
                    </div>
                    <aside style={thumbsContainer}>
                        <h4>Files</h4>
                        {files}
                    </aside>
                    </section>
                )}
            </Dropzone>

             </CardContent>
            </CardActionArea>
            <CardActions className="create-button-panel">
              <IconButton type="submit" aria-label="Assign">
                    <SaveIcon style={styles.taskIcon}/>
                </IconButton>
            </CardActions>
          </Card>
        </form>                 
        );
    }
}

export default Create