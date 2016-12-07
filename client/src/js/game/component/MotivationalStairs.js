import React from "react";
import AppBar from 'material-ui/AppBar';
import Accessibility from 'material-ui/svg-icons/action/accessibility';

import {Container, Row, Col} from 'react-grid-system';

import {Card, CardActions, CardHeader, CardText, CardMedia, CardTitle} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton';

import {Step, Stepper, StepLabel} from 'material-ui/Stepper';

import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

import CircularProgress from 'material-ui/CircularProgress';


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
        step: 0,
        expandedGame: -1,
        selectedGame: null,
        isGameSelect: true,
        hintTexts: [
            "Wählen Sie zunächst das Spiel aus, welches Sie spielen wollen, " +
            "durch einen Klick auf den Namen erscheint eine Beschreibung",

            "In diesem Spiel stehen Ihnen folgende Modi zur Verfügung",

            "Sind Sie bereit? Dann begeben Sie sich auf die Treppe und starten Sie das Spiel!",

            "Sie müssen sich noch kurz gedulden es sind noch ein paar Spieler vor Ihnen dran!"
        ]
    };

    handleNext = (game) => {
        this.setState(
            Object.assign({}, this.state, {
                step: this.state.step+1,
                selectedGame: (game !== undefined)?game: this.state.selectedGame,
                expandedGame: -1
            })
        );
    };

    handlePrev = () => {
        this.setState(
            Object.assign({}, this.state, {
                step: this.state.step-1,
                expandedGame: -1
            })
        );
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
        this.setState(
            Object.assign({}, this.state, {
                isGameSelect: !isGameSelect,
                step: 0
            })
        );
    };

    getStepNavigationComponent = () => {
        return (
        <div style={{marginTop: 12}}>
            <FlatButton
                label="Zurück"
                disabled={this.state.step === 0}
                onTouchTap={this.handlePrev}
                style={{marginRight: 12}}
            />
            <RaisedButton
                label={this.state.step === 2 ? 'Spiel starten' : 'Weiter'}
                primary={true}
                onTouchTap={() => {this.handleNext(this.state.selectedGame)}}
            />
        </div>)
    };

    getStepComponent = (step) => {
        switch (step) {
            case 0:
                return this.props.games.map((game) => {
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
                            <img src={game.image} />
                        </CardMedia>
                    </Card>);
                });
            case 1:
                return (
                    <div style={styles.gameNavigation}>
                        <RadioButtonGroup
                            name="modeSelect"
                        >
                            {
                                this.state.selectedGame.gameModi.map((mode) => {
                                    return <RadioButton
                                        key={mode}
                                        value={mode}
                                        label={mode}
                                    />
                                })
                            }
                        </RadioButtonGroup>
                    { this.getStepNavigationComponent() }
                    </div>);
            case 2:
                return <div style={styles.gameNavigation}>
                        { this.getStepNavigationComponent() }
                    </div>;
            case 3:
                return (
                    <div>
                        <div style={styles.wait}>5</div>
                        <CircularProgress
                            mode="determinate"
                            value={30}
                            size={80}
                            thickness={5}
                        />
                    </div>
                );
            default:
                return "";
        }
    };

    render() {
        return (
        <div>
            <div style={styles.appBar}>
                <AppBar

                    title="MoSt"
                    iconElementLeft={<Accessibility style={styles.header}/>}
                    iconElementRight={
                        <div>
                        {
                            (this.state.isGameSelect)?
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
                    (this.state.isGameSelect)?
                        <Paper zDepth={3}>
                            <Stepper
                                activeStep={this.state.step}
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
                (this.state.isGameSelect)?
                    <Container style={styles.content}>
                        <Row>
                            {
                                (this.state.expandedGame === -1)?
                                    <Paper style={styles.paper} zDepth={2}>
                                        {this.state.hintTexts[this.state.step]}
                                    </Paper>
                                    :
                                    ""
                            }
                        </Row>
                        <Row>
                            { this.getStepComponent(this.state.step) }
                        </Row>
                    </Container>
                :
                    <Container style={styles.table}>
                        <Table
                            selectable={false}
                        >
                            <TableHeader
                                adjustForCheckbox={false}
                                displayRowCheckbox={false}
                                enableSelectAll={false}
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
                                    this.props.highScores.map((score => {
                                       return <TableRow key={score.user.id}>
                                           <TableRowColumn>{score.score}</TableRowColumn>
                                           <TableRowColumn>{score.user.name}</TableRowColumn>
                                           <TableRowColumn>{score.date}</TableRowColumn>
                                       </TableRow>
                                    }))
                                }
                            </TableBody>
                        </Table>
                    </Container>
            }

        </div>);
    }
}

export default MotivationalStairsGame;