const API_LOGIN = "http://localhost:8080/api/auth/login";
const loginBtn = document.getElementById("loginBtn");
const errorEl = document.getElementById("error");
const userInput = document.getElementById("username");
const passInput = document.getElementById("password");

if (localStorage.getItem("jwt")) {
  window.location.href = "home.html";
}

loginBtn.onclick = async () => {
  errorEl.textContent = "";

  const username = userInput.value.trim();
  const password = passInput.value;

  if (!username || !password) {
    errorEl.textContent = "Username and password are required.";
    return;
  }

  try {
    const res = await fetch(API_LOGIN, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });
    if (!res.ok) throw new Error("Invalid username or password");
    const { token } = await res.json();
    localStorage.setItem("jwt", token);
    window.location.href = "home.html";
  } catch (err) {
    errorEl.textContent = err.message;
  }
};
