import React, { Component } from 'react';
import './Task.css';
import { UncontrolledCollapse, Collapse, Button, CardBody, Card , CardImg, CardText,
    CardTitle, CardSubtitle} from 'reactstrap';
import Alert from 'react-s-alert';

class Task extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task:{
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
            }
        };
    }



    componentWillMount() {
        this.setState(
            {
                task:{
                    ...this.state.task,
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
                }
            },function () {
            console.log(this.state.stackId);
        });
       }

    
    render() {
        return (
            <Card className="task">
        <CardBody>
          <CardTitle>{ this.state.task.label }</CardTitle>
          <CardSubtitle>{ this.state.task.category }</CardSubtitle>
          <CardText>{ this.state.task.description }</CardText>
        </CardBody>
      </Card>
        );
    }
}

export default Task