import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function DashboardMedecin({ onNavigate }) {
  const [onglet, setOnglet] = useState("creneaux");
  const [creneaux, setCreneaux] = useState([]);
  const [rendezvous, setRendezvous] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // Formulaire nouveau créneau
  const [form, setForm] = useState({ dateHeure: "", duree: 30 });

  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  const medecinId = user?.id || user?.id_utilisateur || user?.id_utilisateur;

console.log("USER:", user);
  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };


const fetchCreneaux = async () => {
  setLoading(true);
  try {
    const res = await fetch(`${API}/api/creneaux/medecin/${medecinId}`, { headers });
    if (res.ok) setCreneaux(await res.json());
  } catch { setError("Erreur chargement créneaux"); }
  finally { setLoading(false); }
};

  const fetchRendezvous = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/rendezvous/medecin/${medecinId}`, { headers });
      if (res.ok) setRendezvous(await res.json());
    } catch { setError("Erreur chargement rendez-vous"); }
    finally { setLoading(false); }
  };

  useEffect(() => {
    if (onglet === "creneaux") fetchCreneaux();
    if (onglet === "rendezvous") fetchRendezvous();
  }, [onglet]);


  const handleCreateCreneau = async () => {
    if (!form.dateHeure) { setError("Veuillez renseigner la date et l'heure"); return; }
    setLoading(true); setError(""); setSuccess("");
    try {
      const res = await fetch(`${API}/api/creneaux`, {
        method: "POST",
        headers,
        body: JSON.stringify({
          dateHeure: form.dateHeure,
          duree: form.duree,
          disponible: true,
          medecinId: medecinId,
        }),
      });
      if (res.ok) {
        setSuccess("Créneau créé avec succès !");
        setForm({ dateHeure: "", duree: 30 });
        fetchCreneaux();
      } else {
        setError("Erreur lors de la création du créneau.");
      }
    } catch { setError("Impossible de contacter le serveur."); }
    finally { setLoading(false); }
  };

  const handleDeleteCreneau = async (id) => {
    if (!window.confirm("Supprimer ce créneau ?")) return;
    try {
      const res = await fetch(`${API}/api/creneaux/${id}`, { method: "DELETE", headers });
      if (res.ok) {
        setSuccess("Créneau supprimé.");
        fetchCreneaux();
      }
    } catch { setError("Erreur suppression."); }
  };
const handleUpdateStatut = async (id, statut) => {
  try {
    // Récupérer le RDV complet depuis le backend
    const resRdv = await fetch(`${API}/api/rendezvous/${id}`, { headers });
    const rdvComplet = await resRdv.json();
    console.log("RDV depuis backend:", rdvComplet);

    const res = await fetch(`${API}/api/rendezvous/${id}`, {
      method: "PUT",
      headers,
      body: JSON.stringify({
        statut: statut,
        dateHeure: rdvComplet?.dateHeure,
        duree: rdvComplet?.duree,
        patientId: rdvComplet?.patientId,
        medecinId: rdvComplet?.medecinId,
        creneauId: rdvComplet?.creneauId,
      }),
    });
    if (res.ok) {
      setSuccess(`Rendez-vous ${statut === "CONFIRME" ? "confirmé" : "rejeté"} !`);
      fetchRendezvous();
    } else {
      setError("Erreur lors de la mise à jour.");
    }
  } catch { setError("Impossible de contacter le serveur."); }
};
  const formatDate = (dt) => {
    if (!dt) return "";
    return new Date(dt).toLocaleDateString("fr-FR", {
      weekday: "long", day: "numeric", month: "long", year: "numeric",
      hour: "2-digit", minute: "2-digit"
    });
  };

  const handleLogout = () => {
    localStorage.clear();
    onNavigate("home");
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)" }}>

      {/* Header */}
      <div style={{
        background: "white", padding: "16px 24px",
        boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
        display: "flex", justifyContent: "space-between", alignItems: "center"
      }}>
        <div>
          <h1 style={{ fontSize: 20, fontWeight: 700, color: "#0f172a", margin: 0 }}>
            👨‍⚕️ Dr. {user?.nom} {user?.prenom}
          </h1>
          <p style={{ fontSize: 12, color: "#64748b", margin: 0 }}>Tableau de bord médecin</p>
        </div>
        <button onClick={handleLogout} style={{
          padding: "8px 16px", borderRadius: 8, border: "1px solid #e2e8f0",
          background: "white", color: "#64748b", cursor: "pointer", fontSize: 13
        }}>
          Déconnexion
        </button>
      </div>

      <div style={{ maxWidth: 700, margin: "32px auto", padding: "0 24px" }}>

        {/* Onglets */}
        <div style={{ display: "flex", gap: 8, marginBottom: 24 }}>
          {[
            { key: "creneaux", label: "🗓️ Mes Créneaux" },
            { key: "rendezvous", label: "📋 Mes Rendez-vous" },
          ].map((o) => (
            <button key={o.key} onClick={() => setOnglet(o.key)} style={{
              padding: "10px 20px", borderRadius: 10, border: "none", cursor: "pointer",
              fontWeight: 600, fontSize: 14,
              background: onglet === o.key ? "linear-gradient(135deg, #1a5fd4, #0d3d8f)" : "white",
              color: onglet === o.key ? "white" : "#64748b",
              boxShadow: onglet === o.key ? "0 4px 12px rgba(26,95,212,0.3)" : "0 2px 4px rgba(0,0,0,0.06)",
            }}>
              {o.label}
            </button>
          ))}
        </div>

        {/* Messages */}
        {error && (
          <div style={{ padding: "12px 16px", borderRadius: 10, background: "#fee2e2", color: "#991b1b", marginBottom: 16 }}>
            {error}
          </div>
        )}
        {success && (
          <div style={{ padding: "12px 16px", borderRadius: 10, background: "#dcfce7", color: "#166534", marginBottom: 16 }}>
            {success}
          </div>
        )}

        {/* ===== ONGLET CRÉNEAUX ===== */}
        {onglet === "creneaux" && (
          <div>
            {/* Formulaire création */}
            <div style={{
              background: "white", borderRadius: 20, padding: 24,
              boxShadow: "0 4px 16px rgba(26,95,212,0.08)", marginBottom: 24
            }}>
              <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
                ➕ Nouveau créneau
              </h2>
              <div style={{ display: "flex", gap: 12, flexWrap: "wrap" }}>
                <div style={{ flex: 2, minWidth: 200 }}>
                  <label style={{ fontSize: 12, color: "#64748b", display: "block", marginBottom: 6 }}>
                    Date et heure
                  </label>
                  <input
                    type="datetime-local"
                    value={form.dateHeure}
                    onChange={(e) => setForm({ ...form, dateHeure: e.target.value })}
                    style={{
                      width: "100%", padding: "10px 12px", borderRadius: 10,
                      border: "1px solid #e2e8f0", fontSize: 14, boxSizing: "border-box"
                    }}
                  />
                </div>
                <div style={{ flex: 1, minWidth: 120 }}>
                  <label style={{ fontSize: 12, color: "#64748b", display: "block", marginBottom: 6 }}>
                    Durée (min)
                  </label>
                  <select
                    value={form.duree}
                    onChange={(e) => setForm({ ...form, duree: parseInt(e.target.value) })}
                    style={{
                      width: "100%", padding: "10px 12px", borderRadius: 10,
                      border: "1px solid #e2e8f0", fontSize: 14, boxSizing: "border-box"
                    }}
                  >
                    {[15, 20, 30, 45, 60].map(d => (
                      <option key={d} value={d}>{d} minutes</option>
                    ))}
                  </select>
                </div>
              </div>
              <button onClick={handleCreateCreneau} disabled={loading} style={{
                marginTop: 16, padding: "12px 24px", borderRadius: 10, border: "none",
                background: "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
                color: "white", fontWeight: 700, fontSize: 14, cursor: "pointer",
              }}>
                {loading ? "Création..." : "Créer le créneau"}
              </button>
            </div>

            {/* Liste des créneaux */}
            <div style={{
              background: "white", borderRadius: 20, padding: 24,
              boxShadow: "0 4px 16px rgba(26,95,212,0.08)"
            }}>
              <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
                📅 Mes créneaux ({creneaux.length})
              </h2>
              {loading && <p style={{ color: "#64748b" }}>Chargement...</p>}
              {!loading && creneaux.length === 0 && (
                <p style={{ color: "#94a3b8", textAlign: "center", padding: 24 }}>
                  Aucun créneau disponible. Créez-en un !
                </p>
              )}
              {creneaux.map((c) => (
                <div key={c.id_creneau} style={{
                  display: "flex", justifyContent: "space-between", alignItems: "center",
                  padding: "14px 0", borderBottom: "1px solid #f1f5f9"
                }}>
                  <div>
                    <div style={{ fontWeight: 600, color: "#0f172a", fontSize: 14 }}>
                      📅 {formatDate(c.dateHeure)}
                    </div>
                    <div style={{ fontSize: 12, color: "#64748b", marginTop: 4 }}>
                      ⏱️ {c.duree} min &nbsp;•&nbsp;
                      <span style={{ color: c.disponible ? "#16a34a" : "#dc2626" }}>
                        {c.disponible ? "✅ Disponible" : "❌ Indisponible"}
                      </span>
                    </div>
                  </div>
                  <button onClick={() => handleDeleteCreneau(c.id_creneau || c.id)} style={{
                    padding: "6px 14px", borderRadius: 8,
                    border: "1px solid #fecaca", background: "#fee2e2",
                    color: "#dc2626", cursor: "pointer", fontSize: 12, fontWeight: 600
                  }}>
                    Supprimer
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* ===== ONGLET RENDEZ-VOUS ===== */}
        {onglet === "rendezvous" && (
          <div style={{
            background: "white", borderRadius: 20, padding: 24,
            boxShadow: "0 4px 16px rgba(26,95,212,0.08)"
          }}>
            <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
              📋 Mes rendez-vous ({rendezvous.length})
            </h2>
            {loading && <p style={{ color: "#64748b" }}>Chargement...</p>}
            {!loading && rendezvous.length === 0 && (
              <p style={{ color: "#94a3b8", textAlign: "center", padding: 24 }}>
                Aucun rendez-vous pour le moment.
              </p>
            )}
           {rendezvous.map((r) => (
  <div key={r.id_rendezvous} style={{
    padding: "16px 0", borderBottom: "1px solid #f1f5f9"
  }}>
    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
      <div>
        <div style={{ fontWeight: 600, color: "#0f172a", fontSize: 14 }}>
          👤 {r.patientNom}
        </div>
        <div style={{ fontSize: 12, color: "#64748b", marginTop: 4 }}>
          📅 {formatDate(r.dateHeure)} &nbsp;•&nbsp; ⏱️ {r.duree} min
        </div>
      </div>
      <div style={{ display: "flex", gap: 8, alignItems: "center" }}>
        <span style={{
          padding: "4px 12px", borderRadius: 20, fontSize: 11, fontWeight: 700,
          background: r.statut === "PLANIFIE" ? "#dbeafe" : r.statut === "CONFIRME" ? "#dcfce7" : "#fee2e2",
          color: r.statut === "PLANIFIE" ? "#1d4ed8" : r.statut === "CONFIRME" ? "#166534" : "#dc2626",
        }}>
          {r.statut}
        </span>
        {r.statut === "PLANIFIE" && (
          <>
            <button onClick={() => handleUpdateStatut(r.id_rendezvous, "CONFIRME")} style={{
              padding: "6px 12px", borderRadius: 8, border: "none",
              background: "#dcfce7", color: "#166534",
              cursor: "pointer", fontSize: 12, fontWeight: 600
            }}>
              ✅ Confirmer
            </button>
            <button onClick={() => handleUpdateStatut(r.id_rendezvous, "REJETE")} style={{
              padding: "6px 12px", borderRadius: 8, border: "none",
              background: "#fee2e2", color: "#dc2626",
              cursor: "pointer", fontSize: 12, fontWeight: 600
            }}>
              ❌ Rejeter
            </button>
          </>
        )}
      </div>
    </div>
  </div>
))}
          </div>
        )}

      </div>
    </div>
  );
}