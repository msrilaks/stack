import React, { Component } from 'react';
import './Create.css';
import { createTask } from '../../util/APIUtils';
import Alert from 'react-s-alert';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';

class Create extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task:{
            stackId: '',
            category: '',
            label: '',
            description: '',
            userId: ''
            }
        };
        this.state = { newTaskAdded: false };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount() {
        this.setState({
            task:{
                ...this.state.task,
            stackId:this.props.stack.id}
        },function () {
            console.log(this.state.task.stackId);
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
        createTask(createTaskRequest)
        .then(response => {
            Alert.success("Task created successfully!");
            this.props.reloadTasksFunc();
        }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
     
    }

    render() {

        return (
            <form onSubmit={this.handleSubmit}>
            <Card >
            <CardActionArea>
              <CardContent>
                <TextField
                    id="standard-name"
                    label="label"
                    name="label"
                    value={this.state.task.label} onChange={this.handleInputChange} required
                    margin="normal"
                />
                <TextField
                    id="standard-name"
                    label="category"
                    name="category"
                    value={this.state.task.category} onChange={this.handleInputChange} required
                    margin="normal"
                />
                <Typography variant="body2" component="p">
                    <TextField
                        id="standard-name"
                        label="description"
                        name="description"
                        value={this.state.task.description} onChange={this.handleInputChange} required
                        margin="normal"
                    />
                </Typography>
                <TextField
                    id="email-input"
                    label="email"
                    type="email"
                    name="email"
                    autoComplete="email"
                    margin="normal"
                    value={this.state.task.userId} onChange={this.handleInputChange} required
                />
              </CardContent>
            </CardActionArea>
            <CardActions>
              <Button  type="submit" size="small" color="primary">
                Create
              </Button>
            </CardActions>
          </Card>
        </form>                 
        );
    }
}

export default Create