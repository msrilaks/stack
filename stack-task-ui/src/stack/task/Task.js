import React, { Component } from 'react';
import './Task.css';
import Fab from '@material-ui/core/Fab';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import { UncontrolledCollapse, Collapse, Button, CardBody, Card , CardImg, CardText,
    CardTitle, CardSubtitle} from 'reactstrap';
    import { Container, Row, Col } from 'reactstrap';
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
        <Container>
            <Row className="task-buttons">
            <Col>
            <span>
                <Fab size="small" color="secondary" aria-label="Edit">
                    <EditIcon/>
                </Fab>
                </span>
                <span className="task-delete">
                <Fab size="small" aria-label="Delete" >
                    <DeleteIcon />
                </Fab>
                </span>
          </Col>
            </Row>
        <Row className="task-details">
          <Col>
                <CardTitle><span className="task-label">label:</span> { this.state.task.label }</CardTitle>
                <CardSubtitle><span className="task-label">category:</span> { this.state.task.category }</CardSubtitle>
                <CardText><span className="task-label">description:</span> { this.state.task.description }</CardText>
          </Col>
          
        </Row>
        </Container>
        </CardBody>
      </Card>
        );
    }
}

export default Task