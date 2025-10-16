const API = "/api/jugadores";

async function fetchJson(url, opt) {
  const res = await fetch(url, opt);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.status !== 204 ? res.json() : null;
}

async function cargar() {
  const data = await fetchJson(API);
  const tb = document.getElementById('tbl');
  tb.innerHTML = data.map(j => `
    <tr>
      <td>${j.id}</td>
      <td>${j.nombre}</td>
      <td>${j.email ?? ""}</td>
      <td>
        <button class="btn btn-sm btn-info" onclick="ver(${j.id})">Ver</button>
        <button class="btn btn-sm btn-warning" onclick="editar(${j.id})">Editar</button>
        <button class="btn btn-sm btn-danger" onclick="eliminar(${j.id})">Eliminar</button>
      </td>
    </tr>
  `).join('');
}

async function crear(e) {
  e.preventDefault();
  const nombre = document.getElementById('nombre').value.trim();
  const email = document.getElementById('email').value.trim();
  if (!nombre) { alert("El nombre es obligatorio"); return; }

  await fetchJson(API, {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({ nombre, email: email || null })
  });

  e.target.reset();
  cargar();
}

async function ver(id) {
  const j = await fetchJson(`${API}/${id}`);
  alert(`Jugador #${j.id}\nNombre: ${j.nombre}\nEmail: ${j.email ?? "(sin email)"}`);
}

async function editar(id) {
  const j = await fetchJson(`${API}/${id}`);
  const nombre = prompt("Nuevo nombre:", j.nombre);
  if (nombre === null || !nombre.trim()) return;
  const email = prompt("Nuevo email:", j.email ?? "");
  await fetchJson(`${API}/${id}`, {
    method: "PUT",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({ id, nombre: nombre.trim(), email: email?.trim() || null })
  });
  cargar();
}

async function eliminar(id) {
  if (!confirm("¿Eliminar jugador?")) return;
  await fetchJson(`${API}/${id}`, { method: "DELETE" });
  cargar();
}

// Bootstrap
window.addEventListener("DOMContentLoaded", () => {
  if (location.hash) location.hash = "";
  document.getElementById('frmNuevo').addEventListener('submit', crear);
  cargar();
});
