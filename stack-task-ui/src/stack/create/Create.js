import React, { Component } from 'react';
import './Create.css';
import { createTask } from '../../util/APIUtils';
import Alert from 'react-s-alert';

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
                <div className="form-item">
                    <input type="label" name="category" 
                        className="form-control" placeholder="Category"
                        value={this.state.task.category} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <input type="label" name="label" 
                        className="form-control" placeholder="Label"
                        value={this.state.task.label} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <input type="label" name="description" 
                        className="form-control" placeholder="Description"
                        value={this.state.task.description} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <input type="email" name="userId" 
                        className="form-control" placeholder="Email"
                        value={this.state.task.userId} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <button type="submit" className="btn btn-primary">Create</button>
                </div>
            </form>                    
        );
    }
}

export default Create