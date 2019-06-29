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