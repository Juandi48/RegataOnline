const API_MODELOS = "/api/modelos-barcos";

// Función para obtener y renderizar todos los modelos
async function cargarModelos() {
  const res = await fetch(API_MODELOS);
  const modelos = await res.json();
  const tbody = document.querySelector("#tablaModelos tbody");
  tbody.innerHTML = "";

  modelos.forEach(m => {
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td>${m.id}</td>
      <td>${m.nombre}</td>
      <td><span class="badge" style="background:${m.colorHex}">${m.colorHex}</span></td>
      <td>
        <button class="btn btn-info btn-sm ver" data-id="${m.id}">Ver</button>
        <button class="btn btn-warning btn-sm editar" data-id="${m.id}">Editar</button>
        <button class="btn btn-danger btn-sm eliminar" data-id="${m.id}">Eliminar</button>
      </td>`;
    tbody.appendChild(fila);
  });

  // eventos de los botones
  document.querySelectorAll(".ver").forEach(btn => {
    btn.addEventListener("click", e => {
      const id = e.target.dataset.id;
      location.hash = `ver-${id}`; // cambia el hash (ej: #ver-1)
    });
  });
}

// Función para ver un modelo específico
async function verModelo(id) {
  try {
    const res = await fetch(`${API_MODELOS}/${id}`);
    if (!res.ok) throw new Error("Modelo no encontrado");
    const modelo = await res.json();

    const modal = document.getElementById("modalVer");
    modal.querySelector(".modal-title").textContent = `Modelo ${modelo.id}`;
    modal.querySelector(".modal-body").innerHTML = `
      <p><b>Nombre:</b> ${modelo.nombre}</p>
      <p><b>Color:</b> <span style="background:${modelo.colorHex};padding:5px 12px;border-radius:4px">${modelo.colorHex}</span></p>`;
    const bsModal = new bootstrap.Modal(modal);
    bsModal.show();
  } catch (err) {
    alert("Error al cargar modelo: " + err.message);
  }
}

// Detectar cambios en el hash de la URL
window.addEventListener("hashchange", () => {
  const match = location.hash.match(/#ver-(\d+)/);
  if (match) verModelo(match[1]);
});

// Cargar los modelos al inicio
document.addEventListener("DOMContentLoaded", cargarModelos);
