import React from "react";
import PropTypes from "prop-types";
import moment from "moment";
import Chip from '@material-ui/core/Chip';
import FaceIcon from '@material-ui/icons/Face';
// import { Link } from "react-router-dom";
import {
  Card,
  CardHeader,
  CardContent,
  Avatar,
  withStyles,
} from "@material-ui/core";
import colorFrom from "../../utils/colors";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faObjectGroup } from '@fortawesome/free-solid-svg-icons';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";


const styles = (theme) => ({
  card: {
    marginBottom: theme.spacing.unit * 2,
  },
  cardMedia: {
    height: 0,
    paddingTop: "56.25%", // 16:9
  },
  content: {
    wordWrap: "break-word",
  },
  link: {
    textDecoration: "none",
    color: theme.palette.primary.dark,
  },
  root: {
    display: 'flex',
    justifyContent: 'center',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(0.5),
    },
  },
});

const Tweet = ({
  classes,
  id,
  text,
  createdAt,
  identifiedObject,
  tag,
  user: { username },
  highlighted,
}) => {
  const identifiedObjectJSON = (
    <div className={classes.root}>
      {identifiedObject &&
        Object.keys(JSON.parse(identifiedObject)).map((key, i) => {
          const labelText = key + "(" + JSON.parse(identifiedObject)[key] + ")";
          return (
            <Chip
              icon={<FaceIcon />}
              label={labelText}
              color="primary"
              variant="outlined"
            />
          );
        })}
    </div>
  );
  const image = text.length > 100;
 
  return (
    <Card
      key={id}
      component={highlighted ? "div" : "li"}
      className={classes.card}
      elevation={highlighted ? 8 : 1}
    >
      <CardHeader
        avatar={
          <Avatar
            style={{
              backgroundColor: colorFrom(
                typeof tag !== "undefined" ? tag : username
              ),
            }}
          >
            {typeof tag !== "undefined" ? tag[0] : username[0]}
          </Avatar>
        }
        title={typeof tag !== "undefined" ? tag : username}
        // subheader={
        //   <Link to={`/tweet/${id}`} className={classes.link}>
        //     {moment(createdAt).fromNow()}
        //   </Link>
        // }
        subheader={<div>{moment(createdAt).fromNow()}</div>}
      />
      {/* <Link to={`/tweet/${id}`} className={classes.link}> */}
      {image && (
        <div style={{ textAlign: "center" }}>
          <img
            alt="annotated-pic"
            style={{ width: "500px", height: "500px" }}
            src={`data:image/jpeg;base64,${text}`}
          />
        </div>
      )}
      {/* </Link> */}
      <CardContent
        className={classes.content}
        style={{ display: "flex", justifyContent: "center" }}
      >
        <Container fluid>
          <Row>
            <Col xs={1}>
              <FontAwesomeIcon icon={faObjectGroup} size="3x" />
            </Col>
            <Col xs={10} style={{ display: "flex", justifyContent: "center" }}>
              {identifiedObjectJSON}
            </Col>
          </Row>
        </Container>
      </CardContent>
    </Card>
  );
};

Tweet.propTypes = {
  classes: PropTypes.object.isRequired,
  id: PropTypes.string.isRequired,
  text: PropTypes.string.isRequired,
  createdAt: PropTypes.string.isRequired,
  user: PropTypes.shape({
    id: PropTypes.string,
    username: PropTypes.string.isRequired,
  }).isRequired,
  replyToId: PropTypes.string,
  repliedTweet: PropTypes.shape({
    text: PropTypes.string.isRequired,
  }),
  highlighted: PropTypes.bool,
};

Tweet.defaultProps = {
  // replyToId: null,
  repliedTweet: null,
  highlighted: false,
};

export default withStyles(styles)(Tweet);
