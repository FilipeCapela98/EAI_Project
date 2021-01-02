import React from 'react';
import PropTypes from 'prop-types';
import { Paper, TextField, Button, Grid, withStyles } from '@material-ui/core';
import Loader from 'react-loader-spinner';
import { Wave } from 'react-animated-text';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

const styles = theme => ({
  paper: {
    padding: theme.spacing.unit * 2,
  },
  tweetButton: {
    marginTop: theme.spacing.unit,
  },
});

class TweetInput extends React.Component {
  state = {
    onStart: false,
    showText: ''
  };

  constructor(props) {
    super(props);
  }
  static propTypes = {
    classes: PropTypes.object.isRequired,
    onSubmit: PropTypes.func,
  };

  static defaultProps = {
    onSubmit: () => {},
  };

  input = React.createRef();

  onSubmit = event => {
    event.preventDefault();
    const { value } = this.input.current;
    if (!value.trim()) {
      return;
    }
    this.props.onSubmit(value, event);
    this.setState({showText: "Processing hashtag " + this.input.current.value + "..."})
    this.setState({onStart:true});
  };

  handleStop(event) {
    event.preventDefault();
    this.props.onStop(event);
    this.setState({onStart:false});
    this.input.current.value = '';
  }

  reRunAnimation(current) {
    current.setState({show: false});
    setTimeout(()=>{current.setState({show: true});},1000);
  }

  render() {
    const { classes } = this.props;
    return (
      <Paper
        className={classes.paper}
        style={{ position: "sticky", top: "100px" }}
      >
        <form onSubmit={this.onSubmit} autoComplete="off">
          <TextField
            required
            fullWidth
            multiline
            rows={2}
            placeholder="Enter Hashtags?"
            inputRef={this.input}
            disabled={this.state.onStart}
          />
          <Grid container justify="flex-end" spacing={24}>
            <Grid item style={{ paddingRight: "10px" }}>
              <Button
                variant="outlined"
                color="primary"
                type="submit"
                onClick={this.handleStop.bind(this)}
                className={classes.tweetButton}
                value="Stop"
                mt={2}
                disabled={!this.state.onStart}
              >
                Stop
              </Button>
            </Grid>
            <Grid item style={{ paddingLeft: "10px" }}>
              <Button
                variant="outlined"
                color="primary"
                type="submit"
                className={classes.tweetButton}
                value="Send"
                mt={2}
              >
                Start
              </Button>
            </Grid>
          </Grid>
        </form>
        <Row>
          <Col xs={2}>
            <Loader
              type="Puff"
              color="#00BFFF"
              height={50}
              width={50}
              visible={this.state.onStart}
            />
          </Col>
          <Col xs={10} style={{alignSelf:"center"}}>
            <Row>
              {this.state.onStart && (
                <Wave
                  text={this.state.showText}
                  effect="stretch"
                  effectChange={1.5}
                />
              )}
            </Row>
          </Col>
        </Row>
      </Paper>
    );
  }
}

export default withStyles(styles)(TweetInput);
