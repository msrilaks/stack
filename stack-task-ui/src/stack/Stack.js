import React, { Component } from 'react';
import Create from '../stack/create/Create';
import TaskList from '../stack/tasklist/TaskList';
import './Stack.css';
import AddIcon from '@material-ui/icons/Add';
import { getCompletedTasks, getDeletedTasks, getMovedTasks, getTodoTasks, styles } from '../util/APIUtils';
import Alert from 'react-s-alert';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import DeleteIcon from '@material-ui/icons/Delete';
import DoneIcon from '@material-ui/icons/Done';
import CreateIcon from '@material-ui/icons/Create';
import ShareIcon from '@material-ui/icons/Share';
import ClearIcon from '@material-ui/icons/Clear';
import AlarmIcon from '@material-ui/icons/Alarm';
import { If, Then, ElseIf, Else } from 'react-if-elseif-else-render';
import IconButton from '@material-ui/core/IconButton';
import { styled } from '@material-ui/styles';
import { withStyles } from '@material-ui/core/styles';
import { makeStyles } from '@material-ui/core/styles';
import Chip from '@material-ui/core/Chip';

const useStyles = makeStyles(theme => ({
  chipContainer: {
        display: 'flex',
        float: 'left',
        padding: '20px',
        maxRows: '2',
        maxWidth: '700px',
        flexWrap: 'wrap',
        '& > *': {
        margin: theme.spacing(0.5),
        },
    },
}));

class Stack extends Component {
    
    constructor(props) {
        super(props);
        console.log({props});
        this.state = {
            tasks: this.props.stack.tasks,
            todoTasks: null,
            completedTasks: null,
            movedTasks: null,
            deletedTasks: null,
            showCreateTask: false,
            filterTags: '',
            value: 0,
        }

        this.handleChange = this.handleChange.bind(this);
        this.onButtonCreateTaskClicked = this.onButtonCreateTaskClicked.bind(this);
        this.onButtonClearTasksClicked = this.onButtonClearTasksClicked.bind(this);
        this.onButtonRemindTasksClicked = this.onButtonRemindTasksClicked.bind(this);
        this.onButtonDeleteTasksClicked = this.onButtonDeleteTasksClicked.bind(this);
        this.reloadTasks = this.reloadTasks.bind(this);
        this.setFilterTags = this.setFilterTags.bind(this);
        this.deleteFilterTag = this.deleteFilterTag.bind(this);
        this.tagChips = this.tagChips.bind(this);
    }

         
    handleChange(event, newValue) {
        this.setState({value:newValue},function(){
            this.reloadTasks();
        });
        
      }

    componentDidMount() {
          this.setState({
           // tasks:this.props.stack.tasks,
            filterTags: '',
         },function () {
            console.log("stackTasks : "+ this.state.tasks)
        });
        this.reloadTasks();
       }

    deleteFilterTag(chip) {
            var newTagsArr = this.state.filterTags.split(',');
            newTagsArr.splice(newTagsArr.indexOf(chip), 1);
            var newTags = newTagsArr.toString()
            this.setState({
                filterTags : newTags
            },function () {
                    console.log("Task tags - deleted tag:" +chip+" , tags:"+
                    this.state.filterTags);
            });
    }

    setFilterTags(chips) {
        if(this.state.filterTags && this.state.filterTags.trim!=""
                && this.state.filterTags.trim().length>0) {
            var newTagsArr = this.state.filterTags.split(',');
            if(newTagsArr.indexOf(chips) > -1) {
                return;
            }
            var newTags = this.state.filterTags +','+ chips +'';
            this.setState({
                    filterTags : newTags
                },function () {
                    console.log("this.state.filterTags : "
                    + this.state.filterTags)
            });
        } else {
            var newTags = chips +'';
            this.setState({
                    filterTags : newTags
                },function () {
                    console.log("this.state.filterTags : "
                    + this.state.filterTags);
            });
        }
    }

    tagChips() {
    const classes = useStyles();
            return <div className={classes.chipContainer}>
                    {
                        this.state.filterTags
                        && this.state.filterTags.trim() !== ""
                        && this.state.filterTags.length>0
                        && this.state.filterTags.split(',').map(data => {
                        return (
                            <Chip
                                key={data}
                                label={data}
                                size="small"
                                onDelete={()=>this.deleteFilterTag(data)}
                                color="primary"/>
                        );
                    })}
                </div>
    }

    reloadTasks() {
        if(this.state.value == 0){

        getTodoTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                todoTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("todoTasks : "+ this.state.todoTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 
        
    }else if(this.state.value == 1) {

        getMovedTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                movedTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("movedTasks : "+ this.state.movedTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 
      

    }else if(this.state.value == 2){
   
        getCompletedTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                completedTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("completedTasks : "+ this.state.completedTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 
    
    }else if(this.state.value == 3) {
        getDeletedTasks(this.props.stack.id)
        .then(response => {
            this.setState({
                deletedTasks: response, 
                showCreateTask:false,
                
                },function () {
                    console.log("deletedTasks : "+ this.state.deletedTasks)
                });
          }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }); 
    }
    }


    onButtonCreateTaskClicked() {
        this.setState({
            showCreateTask: !this.state.showCreateTask,
        });
    }

    onButtonClearTasksClicked() {

    }

    onButtonDeleteTasksClicked() {

    }

    onButtonRemindTasksClicked(){

    }

    render() {
        const StackTabs = styled(Tabs)({
                    '& div': {
                        backgroundColor: 'white',
                        border: 'none',
                        borderBottomColor: 'white',
                        height: '50px',
                    },
                    '& span': {
                        color: '#1a9099',
                        fontWeight: '300',
                    }
                });
        return (
           <div>
            <Paper
                elevation='0'>
                <StackTabs value={this.state.value}
                    onChange={this.handleChange}
                    centered
                    indicatorColor="primary"
                    style={styles.stackTab}>
                <Tab label="To Do"></Tab>
                <Tab label="Pushed"/>
                <Tab label="Completed"/>
                <Tab label="Deleted"/>
                </StackTabs>
            </Paper>
             <div className="stack-container" style={styles.stackContainer}>
             <this.tagChips/>
            <div className="stack-task-container" style={styles.stackTaskContainer}>
            {

                <If condition={this.state.value === 0}>
                   <Then>
                   <div>
                    <IconButton aria-label="Add" onClick={this.onButtonCreateTaskClicked}>
                        <AddIcon  style={styles.stackIcon}/>
                    </IconButton>

                    {this.state.showCreateTask ?
                            <Create  authenticated={this.props.authenticated} 
                                    currentUser={this.props.currentUser} 
                                    stack={this.props.stack} 
                                    reloadTasksFunc={this.reloadTasks}
                                    taskProfile='created'/>
                                    :null}
                    </div>
                   <TaskList tasks={this.state.todoTasks}
                        reloadTasks={this.reloadTasks}
                        setFilterTags={this.setFilterTags}
                        authenticated={this.props.authenticated}
                        currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='todo'/>
                   </Then>
                   <ElseIf condition={this.state.value === 1 }>
                        <div>
                        <IconButton aria-label="Alarm" onClick={this.onButtonRemindTasksClicked}>
                            <AlarmIcon  style={styles.stackIcon}/>
                        </IconButton>
                        </div>
                         <TaskList tasks={this.state.movedTasks}
                             reloadTasks={this.reloadTasks}
                             setFilterTags={this.setFilterTags}
                             authenticated={this.props.authenticated}
                             currentUser={this.props.currentUser}
                             stack={this.props.stack}
                         taskProfile='pushed'/>
                    </ElseIf>
                   <ElseIf condition={this.state.value === 2 }>
                        <div>
                            <IconButton aria-label="Delete" onClick={this.onButtonDeleteTasksClicked}>
                                <DeleteIcon  style={styles.stackIcon}/>
                            </IconButton>
                        </div>
                         <TaskList tasks={this.state.completedTasks}
                            reloadTasks={this.reloadTasks}
                            setFilterTags={this.setFilterTags}
                            authenticated={this.props.authenticated}
                         currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='completed'/>
                    </ElseIf>
                    <ElseIf condition={this.state.value === 3}>
                    <div>
                    <IconButton aria-label="Clear" onClick={this.onButtonClearTasksClicked}>
                        <ClearIcon  style={styles.stackIcon}/>
                    </IconButton>
                    </div>
                    <TaskList tasks={this.state.deletedTasks}
                        reloadTasks={this.reloadTasks}
                        setFilterTags={this.setFilterTags}
                        authenticated={this.props.authenticated}
                        currentUser={this.props.currentUser}
                        stack={this.props.stack}
                        taskProfile='deleted'/>
                    </ElseIf>
                </If>
            }
            </div>

     </div>
     </div>
    );
    }
}

export default Stack