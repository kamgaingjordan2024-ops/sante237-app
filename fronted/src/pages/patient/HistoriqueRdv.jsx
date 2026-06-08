import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function HistoriqueRdv({ onNavigate }) {
  const [rendezvous, setRendezvous] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filtre, setFiltre] = useState("TOUS");

  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  const patientId = user?.id;

  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };

  const fetchRendezvous = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/rendezvous/patient/${patientId}`, { headers });
      if (res.ok) setRendezvous(await res.json());
    } catch { console.error("Erreur chargement"); }
    finally { setLoading(false); }
  };

  useEffect(() => { fetchRendezvous(); }, []);

  const formatDate = (dt) => {
    if (!dt) return "";
    return new Date(dt).toLocaleDateString("fr-FR", {
      weekday: "long", day: "numeric", month: "long", year: "numeric",
      hour: "2-digit", minute: "2-digit"
    });
  };

  const filtres = ["TOUS", "PLANIFIE", "CONFIRME", "REJETE"];

  const rdvFiltres = filtre === "TOUS"
    ? rendezvous
    : rendezvous.filter(r => r.statut === filtre);

  const statutStyle = (statut) => {
    switch (statut) {
      case "PLANIFIE": return { background: "#dbeafe", color: "#1d4ed8" };
      case "CONFIRME": return { background: "#dcfce7", color: "#166534" };
      case "REJETE": return { background: "#fee2e2", color: "#dc2626" };
      default: return { background: "#f1f5f9", color: "#64748b" };
    }
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 600, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-hopital")} style={{
          background: "none", border: "none", cursor: "pointer",
          color: "#1a5fd4", fontSize: 14, fontWeight: 600, marginBottom: 24,
        }}>
          ← Retour
        </button>

        <h1 style={{ fontSize: 24, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          📋 Mes Rendez-vous
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Historique de tous vos rendez-vous
        </p>

        {/* Filtres */}
        <div style={{ display: "flex", gap: 8, marginBottom: 24, flexWrap: "wrap" }}>
          {filtres.map(f => (
            <button key={f} onClick={() => setFiltre(f)} style={{
              padding: "8px 16px", borderRadius: 20, border: "none",
              cursor: "pointer", fontSize: 13, fontWeight: 600,
              background: filtre === f ? "#1a5fd4" : "white",
              color: filtre === f ? "white" : "#64748b",
              boxShadow: "0 2px 4px rgba(0,0,0,0.06)",
            }}>
              {f}
            </button>
          ))}
        </div>

        {loading && <p style={{ color: "#64748b" }}>Chargement...</p>}

        {!loading && rdvFiltres.length === 0 && (
          <div style={{
            background: "white", borderRadius: 20, padding: 32,
            textAlign: "center", color: "#94a3b8"
          }}>
            Aucun rendez-vous trouvé.
          </div>
        )}

        {rdvFiltres.map((r) => (
          <div key={r.id_rendezvous} style={{
            background: "white", borderRadius: 16, padding: 20,
            marginBottom: 12, boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
          }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
              <div>
                <div style={{ fontWeight: 700, color: "#0f172a", fontSize: 15, marginBottom: 6 }}>
                  👨‍⚕️ Dr. {r.medecinNom}
                </div>
                <div style={{ fontSize: 13, color: "#64748b", marginBottom: 4 }}>
                  📅 {formatDate(r.dateHeure)}
                </div>
                <div style={{ fontSize: 13, color: "#64748b" }}>
                  ⏱️ {r.duree} minutes
                </div>
              </div>
              <span style={{
                padding: "4px 12px", borderRadius: 20, fontSize: 11, fontWeight: 700,
                ...statutStyle(r.statut)
              }}>
                {r.statut}
              </span>
            </div>
          </div>
        ))}

      </div>
    </div>
  );
}