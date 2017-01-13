import React from "react";
import AppBar from 'material-ui/AppBar';
import Accessibility from 'material-ui/svg-icons/action/accessibility';

import {Container, Row} from 'react-grid-system';

import {Card, CardActions, CardHeader, CardText, CardMedia, CardTitle} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton';

import {Step, Stepper, StepLabel} from 'material-ui/Stepper';

import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

import {List, ListItem} from 'material-ui/List';
import LinearProgress from 'material-ui/LinearProgress';

import Dialog from 'material-ui/Dialog';
import TextField from 'material-ui/TextField';
import ContentDrafts from 'material-ui/svg-icons/content/drafts';
import Divider from 'material-ui/Divider';

const styles = {
    header: {
        color: "white",
        height: 46
    },

    appBar: {
        position: "fixed",
        width: "100%",
        zIndex: 1000
    },

    content: {
        paddingTop: 150,
        zIndex: 1
    },

    paper: {
        marginLeft: 20,
        marginRight: 20,
        marginBottom: 20,
        padding: 10,
        color: "black",
        textAlign: "center"
    },

    gameNavigation: {
        margin: 20
    },

    table: {
       paddingTop: 60
    },

    wait: {

    }
};


class MotivationalStairsGame extends React.Component {

    state = {
        expandedGame: -1,
        selectedGame: null,
        gameModeSelect: 1,
        hintTexts: [
            "Wähle zunächst das Spiel aus, welches du spielen willst, " +
            "durch einen Klick auf den Namen erscheint die Beschreibung Beschreibung",

            "In diesem Spiel stehen dir folgende Modi zur Verfügung. Wähle einen Spieler, mit dem du zusammen spielen willst.",

            "Bist du bereit?? Dann begib dich auf die Treppe und starte das Spiel!",

            "Es kann sein, dass spieler vor dir dran sind, in dem Fall wird eine Liste angezeigt."
        ]
    };

    handleNext = (game) => {
        this.setState(
            Object.assign({}, this.state, {
                selectedGame: (game !== undefined)?game: this.state.selectedGame,
                expandedGame: -1
            })
        );
        this.props.model.API.setStep(this.props.model.step+1);
    };

    handlePrev = () => {
        this.setState(
            Object.assign({}, this.state, {
                step: this.props.model.step-1,
                expandedGame: -1
            })
        );
        this.props.model.API.setStep(this.props.model.step-1);
    };

    handleExpandChange = (id, expanded) => {
        const target = expanded?id:-1;
        this.setState(
            Object.assign({}, this.state, {
                expandedGame: target
            })
        );
    };

    handleMenu = (isGameSelect) => {
        this.props.model.API.setStep(0);
        this.props.model.API.setGameSelect(!isGameSelect);
    };

    getStepNavigationComponent = () => {
        return (
        <div style={{marginTop: 12}}>
            <FlatButton
                label="Zurück"
                disabled={this.props.model.step === 0 || this.props.model.askedPlayer !== 0}
                onTouchTap={this.handlePrev}
                style={{marginRight: 12}}
            />
            <RaisedButton
                label={this.props.model.step === 2 ? 'Spiel starten' : 'Weiter'}
                disabled={this.props.model.step===1 && (this.state.gameModeSelect === 0 || this.props.model.askedPlayer === 0)}
                primary={true}
                onTouchTap={() => {this.handleNext(this.state.selectedGame)}}
            />
        </div>)
    };

    getStepComponent = (step, props) => {
        switch (step) {
            case 0:
                return this.props.model.games.map((game) => {
                    return (
                    <Card
                        key={game.id}
                        expanded={this.state.expandedGame === game.id}
                        onExpandChange={(expanded) => {this.handleExpandChange(game.id, expanded)}}
                    >
                        <CardHeader
                            title={game.name}
                            subtitle={game.subtitle}
                            actAsExpander={true}
                            showExpandableButton={true}
                        />
                        <CardActions>
                            <RaisedButton
                                primary={true}
                                label={game.name + " Spielen"}
                                onTouchTap={() => {this.handleNext(game)} }
                            />
                        </CardActions>
                        <CardText expandable={true}>
                            {game.description}
                        </CardText>
                        <CardMedia
                            expandable={true}
                            overlay={
                                <CardTitle
                                    title={game.name}
                                    subtitle="Spielfeld"
                                />
                            }
                        >
                            <img role="presentation" src={game.imagePath} />
                        </CardMedia>
                    </Card>);
                });
            case 1:
                return (
                    <div style={styles.gameNavigation}>
                        <RadioButtonGroup
                            name="modeSelect"
                            valueSelected={this.state.gameModeSelect}
                            onChange={(evt, val) => { this.setState(Object.assign({}, this.state, { gameModeSelect: val }))}}
                        >
                            {
                                this.state.selectedGame.gameModes.map((mode, index) => {
                                    return <RadioButton
                                        key={index}
                                        value={index}
                                        label={mode}
                                    />
                                })
                            }
                        </RadioButtonGroup>
                        <Row>
                            <h3 style={ {marginLeft: "20px"} } >Aktive Spieler:</h3>
                        </Row>
                        <List>
                            {
                                props.model.opponents.map((player) => {
                                    if(player.id !== props.model.user.id) {
                                        return (
                                            <div key={player.id}>
                                                <ListItem
                                                    primaryText={player.name}
                                                    onTouchTap={function() {
                                                        props.model.API.askOpponentForPlay(player.id);
                                                    }}
                                                    disabled={player.asked === -2}
                                                    rightIcon={(player.asked === -2)?<ContentDrafts />:undefined}
                                                />
                                                <Divider/>
                                            </div>

                                        );
                                    } else {
                                        return "";
                                    }
                                })
                            }
                        </List>
                    { this.getStepNavigationComponent() }
                    </div>);
            case 2:
                return <div style={styles.gameNavigation}>
                        { this.getStepNavigationComponent() }
                    </div>;
            case 3:
                return props.model.queue.length>0?(
                    <div>
                        <LinearProgress mode="determinate" value={(((props.model.gameTicket !== null)?props.model.gameTicket.usersBefore.length:0) / props.model.queue.length) * 100}/>
                        <Paper style={styles.paper} zDepth={1}>
                            <List>
                                {
                                    (props.model.gameTicket !== null)?props.model.gameTicket.usersBefore.map((users) => {
                                        return(
                                            <ListItem primaryText={users}/>
                                        );
                                    }):""
                                }
                            </List>
                        </Paper>
                    </div>
                ):undefined;
            default:
                return "";
        }
    };

    getPlayRequestDialogComponent = (model) => {
        if(model.playRequest) {
            return (
                <Dialog
                    title={model.playRequest.user.name + " fragt"}
                    modal={false}
                    actions={[
                        <FlatButton
                            label="Nein"
                            primary={true}
                            onTouchTap={function() {
                                model.API.answerPlayRequest(false);
                            }}
                        />,
                        <FlatButton
                            label="Ja"
                            primary={true}
                            onTouchTap={function() {
                                model.API.answerPlayRequest(true);
                            }}
                            keyboardFocused={true}
                        />
                    ]}
                    open={model.playRequest.state}
                >
                    Wollen wir zusammen Pong spielen?
                </Dialog>);
        } else {
            return "";
        }
    };

    getNotificationDialogComponent = (model) => {
        if(model.notification) {
            return (
                <Dialog
                    title={model.notification.message}
                    modal={false}
                    actions={[
                        <FlatButton
                            label="Ok"
                            primary={true}
                            onTouchTap={function() {
                                model.API.dismissNotification(true);
                            }}
                        />
                    ]}
                    open={model.notification.state}
                    onRequestClose={function() {
                        model.API.dismissNotification(false);
                    }}
                >
                    { model.notification.detail }
                </Dialog>
            );
        }
    };

    getChangeUserNameDialogComponent = (model) => {
        if(model.changeUserName) {
            return (
                <Dialog
                    title="Benutzernamen ändern"
                    modal={false}
                    actions={[
                        <FlatButton
                            label="Abbrechen"
                            primary={true}
                            onTouchTap={() => {
                                model.API.closeUserNameDialog();
                            }}
                        />,
                        <FlatButton
                            label="Speichern"
                            primary={true}
                            onTouchTap={() => {
                                model.API.saveNewUserName();
                            }}
                            disabled={model.user.name.length===0}
                        />
                    ]}
                    open={model.changeUserName}
                >
                    <TextField
                        floatingLabelText="Gib deinen Namen ein"
                        fullWidth={true}
                        errorText={(model.user.name.length>0)?"":"Der Name darf nicht leer sein"}
                        value={model.user.name}
                        onChange={(e) => { model.API.setNewUserName(e.target.value); }}
                    />
                </Dialog>
            );
        }
    };

    render() {
        const model = this.props.model;
        return (
        <div>
            <div style={styles.appBar}>
                <AppBar
                    title={this.props.model.user.name}
                    onTitleTouchTap={() => { model.API.openUserNameDialog(); }}
                    showMenuIconButton={false}
                    titleStyle={{fontSize: "17px", fontWeight: 100}}
                    iconElementRight={
                        <div>
                        {
                            (this.props.model.isGameSelect)?
                                <div>
                                    <RaisedButton
                                        disabled={true}
                                        label="Spielen"
                                        style={styles.header}/>
                                    <FlatButton
                                        label="Highscores"
                                        style={styles.header}
                                        onTouchTap={() => this.handleMenu(true)}/>
                                </div>
                            :
                                <div>
                                    <FlatButton
                                        label="Spielen"
                                        style={styles.header}
                                        onTouchTap={() => this.handleMenu(false)}/>
                                    <RaisedButton
                                        disabled={true}
                                        label="Highscores"
                                        style={styles.header}/>
                                </div>
                        }
                        </div>
                    }
                />
                {
                    (this.props.model.isGameSelect)?
                        <Paper zDepth={3}>
                            <Stepper
                                activeStep={this.props.model.step}
                            >
                                <Step>
                                    <StepLabel>Spiel</StepLabel>
                                </Step>
                                <Step>
                                    <StepLabel>Modus</StepLabel>
                                </Step>
                                <Step>
                                    <StepLabel>Spiel starten</StepLabel>
                                </Step>
                            </Stepper>
                        </Paper>
                    :
                        ""
                }
            </div>
            {
                (this.props.model.isGameSelect)?
                    <Container style={styles.content}>
                        <Row>
                            {
                                (this.state.expandedGame === -1)?
                                    <Paper style={styles.paper} zDepth={2}>
                                        {this.state.hintTexts[this.props.model.step]}
                                    </Paper>
                                    :
                                    ""
                            }
                        </Row>
                        <Row>
                            { this.getStepComponent(this.props.model.step, this.props) }
                        </Row>
                    </Container>
                :
                    <Container style={styles.table}>
                        <Table
                            selectable={false}
                            multiSelectable={false}
                            fixedHeader={true}
                        >
                            <TableHeader
                                adjustForCheckbox={false}
                                displayRowCheckbox={false}
                                enableSelectAll={false}
                                displayHeaderSelectAll={false}
                            >
                                <TableRow>
                                    <TableHeaderColumn>Punkte</TableHeaderColumn>
                                    <TableHeaderColumn>Name</TableHeaderColumn>
                                    <TableHeaderColumn>Datum</TableHeaderColumn>
                                </TableRow>
                            </TableHeader>
                            <TableBody
                                displayRowCheckbox={false}
                                adjustForCheckbox={false}
                            >
                                {
                                    this.props.model.highscores.map(((score,id) => {
                                        var rowStyle = {};
                                        if(score.userId === this.props.model.user.id) {
                                            rowStyle.backgroundColor = "#76d1ff";
                                            rowStyle.borderBottom = "1px solid rgb(101, 180, 247)";
                                        }
                                       return <TableRow key={score.userId} style={rowStyle}>
                                           <TableRowColumn style={{width: 35}}>{score.highscore}</TableRowColumn>
                                           <TableRowColumn>{score.userName}</TableRowColumn>
                                           <TableRowColumn style={{width: 68}}>{new Date(score.created).toLocaleDateString()}</TableRowColumn>
                                       </TableRow>
                                    }))
                                }
                            </TableBody>
                        </Table>
                    </Container>
            }
            <div>
                { this.getPlayRequestDialogComponent(this.props.model) }
                { this.getNotificationDialogComponent(this.props.model) }
                { this.getChangeUserNameDialogComponent(this.props.model) }
            </div>
        </div>);
    }


}

export default MotivationalStairsGame;