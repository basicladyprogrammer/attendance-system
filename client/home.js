document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("jwt");
  if (!token) {
    window.location.href = "login.html";
    return;
  }

  const doLogout = () => {
    localStorage.removeItem("jwt");
    window.location.href = "login.html";
  };

  const logoutBtnTop = document.getElementById("logoutBtn");
  const logoutBtnHero = document.getElementById("logoutBtnHero");
  if (logoutBtnTop) logoutBtnTop.addEventListener("click", doLogout);
  if (logoutBtnHero) logoutBtnHero.addEventListener("click", doLogout);

  const markBtn = document.getElementById("markBtn");
  if (markBtn) {
    markBtn.addEventListener("click", () => {
      alert("Attendance marking coming soon!");
    });
  }
});
