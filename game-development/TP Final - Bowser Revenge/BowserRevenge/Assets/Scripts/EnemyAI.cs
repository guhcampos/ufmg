using UnityEngine;
using System.Collections;
using Pathfinding;

public class EnemyAI : MonoBehaviour {

    public GameObject startObject;
    public GameObject targetObject;

    public Path path;

    public float speed = 32;
    public float nextWaypointDistance = 3;

    private Vector3 StartPosition;
    private Vector3 TargetPosition;

    private Seeker seeker;
    private CharacterController controller;

    private int currentWaypoint;

    public void Start () {
        
        seeker = GetComponent<Seeker>();
        controller = GetComponent<CharacterController>();


        if(startObject == null) {
            startObject = GameObject.FindWithTag("Respawn");
        }

        if(targetObject == null) {
            targetObject = GameObject.FindWithTag("Finish");
        }
        
        StartPosition = startObject.transform.position;
        TargetPosition = targetObject.transform.position;

        //Start a new path to the targetPosition, return the result to the OnPathComplete function
        seeker.StartPath(StartPosition, TargetPosition, OnPathComplete);
    }
    
    public void OnPathComplete (Path p) {
        Debug.Log ("Yey, we got a path back. Did it have an error? "+p.error);
        if (!p.error) {
            path = p;
            //Reset the waypoint counter
            currentWaypoint = 0;
        }
    }

    public void FixedUpdate () {
        if (path == null) {
            //We have no path to move after yet
            return;
        }
        
        if (currentWaypoint >= path.vectorPath.Count) {
            Debug.Log ("End Of Path Reached");
            return;
        }
        
        //Direction to the next waypoint
        Vector3 dir = (path.vectorPath[currentWaypoint]-transform.position).normalized;
        dir *= speed * Time.fixedDeltaTime;
        controller.SimpleMove (dir);
        
        //Check if we are close enough to the next waypoint
        //If we are, proceed to follow the next waypoint
        if (Vector3.Distance (transform.position,path.vectorPath[currentWaypoint]) < nextWaypointDistance) {
            currentWaypoint++;
            return;
        }
    }
}
