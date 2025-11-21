const API = "/api/v1"; // importantísimo: prefijo de los REST nuevos
const out = document.getElementById("out");
const grid = document.getElementById("grid");

function log(x){ out.textContent = typeof x === "string" ? x : JSON.stringify(x, null, 2); }
async function getJSON(u, opt){ const r = await fetch(u, opt); if(!r.ok) throw new Error(r.status+" "+r.statusText); return r.json(); }

function drawGrid(mapa, barco){
  if(!mapa){ grid.innerHTML=""; return; }
  grid.style.setProperty('--w', mapa.ancho);
  grid.innerHTML = "";
  const tipos = new Map((mapa.celdas||[]).map(c => [`${c.x},${c.y}`, c.tipo]));
  for(let y=0;y<mapa.alto;y++){
    for(let x=0;x<mapa.ancho;x++){
      const d = document.createElement('div');
      const t = (tipos.get(`${x},${y}`) || "PARED").toLowerCase();
      d.className = `cell cell-${t}`;
      if (barco && barco.posX===x && barco.posY===y) d.classList.add("cell-barco");
      grid.appendChild(d);
    }
  }
}

document.getElementById("btnStart").onclick = async ()=>{
  try {
    const data = await getJSON(`${API}/game/start?mapaId=1&barcoId=1`, { method:'POST' });
    log(data); drawGrid(data.mapa, data.barco);
  } catch(e){ log("Error start: "+e.message); console.error(e); }
};

document.getElementById("btnState").onclick = async ()=>{
  try {
    const data = await getJSON(`${API}/game/state`);
    log(data); drawGrid(data.mapa, data.barco);
  } catch(e){ log("Error state: "+e.message); console.error(e); }
};

document.getElementById("btnMove").onclick = async ()=>{
  try {
    const data = await getJSON(`${API}/game/move`, {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify({ deltaVX:1, deltaVY:0 })
    });
    log(data); drawGrid(data.mapa, data.barco);
  } catch(e){ log("Error move: "+e.message); console.error(e); }
};

console.log("app.js cargado ✅");
