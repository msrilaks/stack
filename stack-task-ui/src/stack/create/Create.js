import React, { Component } from 'react';
import './Create.css';
import { createTask, uploadPhoto, modifyTask, base64toBlob, deletePhoto, styles } from '../../util/APIUtils';
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
import Dropzone from 'react-dropzone'

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
        this.removeFile = this.removeFile.bind(this);
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

    render() {

        const files = this.state.files.map(file => (
            <div style={styles.thumb} key={file.name}>
            <div style={styles.thumbIcon} onClick={() => this.removeFile(file)}>
              <DeleteIcon style={styles.taskIcon} />
            </div>
            <div style={styles.thumbInner}>
              <img
                src={file.preview}
                name={file.name}
                style={styles.img}
              />

            </div>
            </div>
        ))
                let UploadPanel = <aside style={styles.thumbsContainer}></aside>;
                if(files && files.length >0) {
                    UploadPanel = <aside style={styles.thumbsContainer}>
                        {files}
                    </aside>
                }
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
                />
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
                */}
                <TextField
                    id="standard-required"
                    label="tags"
                    name="tags"
                    defaultValue="default"
                    style={styles.taskTextField}
                    value={this.state.task.category} onChange={this.handleInputChange} required
                    margin="normal"
                />

                <Typography variant="body2" component="p">
                    <TextField
                        id="standard-multiline-flexible"
                        label="description"
                        multiline
                        fullWidth
                        rowsMax="4"
                        name="description"
                        value={this.state.task.description} onChange={this.handleInputChange} required
                        margin="normal"
                    />
                </Typography>
            <Dropzone onDrop={this.onDrop}>
                {({getRootProps, getInputProps}) => (
                    <section className="container">
                    <div {...getRootProps({className: 'dropzone'})}>
                      <input {...getInputProps()} />
                      <p>Drag 'n' drop files here, or click to select files</p>
                    </div>

                    </section>
                )}
            </Dropzone>
            {UploadPanel}
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