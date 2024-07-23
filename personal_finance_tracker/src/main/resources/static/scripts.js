/**
 * 
 */

//this function is work like calling to backend API
function signup(){
	var fullname =document.getElementById("fname").value;
	var phone=document.getElementById("phone").value;
	var email=document.getElementById("email").value;
	var address=document.getElementById("address").value;
	var password=document.getElementById("password").value;
	var cpass=document.getElementById("cpass").value;
	var status=document.getElementById("data");
	alert("hii");
		
				fetch('signup/user',{
					method:'POST',
					headers:{
						'Content-Type':'application/json'
					},
					body:JSON.stringify({
						userName:fullname,
						userPhone:phone,
						userEmail:email,
						userAddress:address,
						password:password,
						wallet:{
							walletAmount:0
						}
					}
					)
					
				})
				.then(function(response){ 
  return response.json()})
  .then(function(data)
  {
	  alert(data);
  title=document.getElementById("title")
  body=document.getElementById("bd")
  title.innerHTML = data.title
  body.innerHTML = data.body  

				
	});
}

//LOGIN

function login(){
	
	let phone=document.getElementById("phone");
	let password=document.getElementById("password");
	
	alert("hiii");
	fetch('/login/user/'+phone+'/'+password)
	
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(userData => {
    // Process the retrieved user data
    console.log('User Data:', userData);
    window.location("home.html");
    
  })
  .catch(error => {
    console.error('Error:', error);
  });
	
}
//
function cancle(){
	window.location.href="index.html";
}

// Toggle between showing and hiding the sidebar when clicking the menu icon
var mySidebar = document.getElementById("mySidebar");

function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
  } else {
    mySidebar.style.display = 'block';
  }
}

// Close the sidebar with the close button
function w3_close() {
    mySidebar.style.display = "none";
}