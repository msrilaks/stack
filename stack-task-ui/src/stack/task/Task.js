import React, { Component } from 'react';
import './Task.css';
import Alert from 'react-s-alert';

class Task extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            stackId: '',
            category: '',
            label: '',
            description: '',
            userId: '',
            createdByUserId: '',
            origin: '',
            createdTimeStamp: '',
            lastModifiedTimeStamp: '',
            completedTimeStamp:'',
            movedTimeStamp: '',
            deletedTimeStamp: ''
        };
    }

    componentWillMount() {
        this.setState(
            {
                stackId:this.props.task.stackId,
                id:this.props.task.id,
                category: this.props.task.category,
                label: this.props.task.label,
                description: this.props.task.description,
                userId: this.props.task.userId,
                createdByUserId: this.props.task.createdByUserId,
                origin: this.props.task.origin,
                createdTimeStamp: this.props.task.createdTimeStamp,
                lastModifiedTimeStamp: this.props.task.lastModifiedTimeStamp,
                completedTimeStamp:this.props.task.completedTimeStamp,
                movedTimeStamp: this.props.task.movedTimeStamp,
                deletedTimeStamp: this.props.task.deletedTimeStamp
            },function () {
            console.log(this.state.stackId);
        });
       }

    
    render() {
        return (
            <div className="task-container">
               <div className="task">
                    <div>{ this.state.category }</div>
                    <div>{ this.state.label }</div>
                    <div>{ this.state.description }</div>
                </div>
            </div>                    
        );
    }
}

export default Task