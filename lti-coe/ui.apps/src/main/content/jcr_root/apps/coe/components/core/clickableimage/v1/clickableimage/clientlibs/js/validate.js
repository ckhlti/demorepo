function validateform(){
var id1 = document.loginForm.id;
var firstname = document.loginForm.firstname;
var lastname = document.loginForm.lastname;
var dob = document.loginForm.dob;
var gender = document.loginForm.gender;
var mob = document.loginForm.mob;
var email = document.loginForm.email;
var pan = document.loginForm.pan;
var aadhar = document.loginForm.aadhar;
var address= document.loginForm.address;
var city = document.loginForm.city;
var state = document.loginForm.state;

if (firstname.value == ""||lastname.value==""||dob.value==""||mob.value==""||email.value==""||pan.value==""||
aadhar.value==""||address.value==""||city.value==""||state.value=="") { 
                    alert("Please enter all fields!!"); 
                    return false; 
                }

if(allLetter(firstname))
{
if(allLetter2(lastname))
{ 

if(Validatemob(mob))
{
if(ValidateEmail(email))
{

if(ValidateAadhar(aadhar))
{
return true;
}
} 
}
}
}

return false;

}



function allLetter(firstname)
{
var letters = /^[A-Za-z]+$/;
if(firstname.value.match(letters))
{
return true;
}
else
{
alert('Firstname must have alphabet characters only!!');
firstname.focus();
return false;
}
}


function allLetter2(lastname){
var letters = /^[A-Za-z]+$/;
if(lastname.value.match(letters))
{
return true;
}
else
{
alert('Lastname must have alphabet characters only!!');
lastname.focus();
return false;
}
}

function Validatemob(mob){
var numbers = /^[0-9]+$/;
if(mob.value.match(numbers)&&mob.value.length==10)
{
return true;
}
else
{
alert('mob must have numeric characters and 10 digits only!!');
mob.focus();
return false;
}
}

function ValidateAadhar(aadhar){
var numbers = /^[0-9]+$/;
if(aadhar.value.match(numbers)&&aadhar.value.length==12)
{
return true;
}
else
{
alert('Aadhar number must have numeric characters and 12 digits only!!');
aadhar.focus();
return false;
}
}

function ValidateEmail(email){
var mailformat = /^\w+([\.-]?\w+)@\w+([\.-]?\w+)(\.\w{2,3})+$/;
if(email.value.match(mailformat))
{
return true;
}
else
{
alert("Please enter valid Email Address!!");
email.focus();
return false;
}
}