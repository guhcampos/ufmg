#pragma strict

public class Planet extends MonoBehaviour {

	private var OrbitBaseSpeed : float;
	private var OrbitBaseInclination : Vector3;
	private var OrbitInclinationExaggeration : float;
	
	private var BaseRotationSpeed : float;
	private var RotationExaggeration : float;

	public var SpeedModifier : float;
	public var InclinationModifier : float;
	public var RotationModifier : float;
	
	public function Start() {
		OrbitBaseSpeed = 10;
		OrbitBaseInclination = Vector3.up;
		OrbitInclinationExaggeration = 2;
		BaseRotationSpeed = 1;
		RotationExaggeration = 1;
	}

	public function Update() {
	
		var OrbitAngle = InclinationModifier * OrbitInclinationExaggeration;

		transform.Rotate(
			Vector3(
				0, 
				BaseRotationSpeed * RotationExaggeration * RotationModifier,
				0
			) * Time.deltaTime
		);

		transform.RotateAround(
			transform.parent.position,
			Quaternion.Euler(0,0, OrbitAngle) * OrbitBaseInclination,
			OrbitBaseSpeed * SpeedModifier * Time.deltaTime
		);
	}
}