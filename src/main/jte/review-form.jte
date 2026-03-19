@import org.example.sbmdb.entity.dto.ReviewDTO
@import gg.jte.Content

@param Long movieId
@param ReviewDTO review = null

!{String pageTitle = review != null ? "Edit Review" : "Add Review";}
!{String formAction = review != null ? "/reviews/" + review.id() + "/edit" : "/reviews/create";}
!{String submitLabel = review != null ? "Save Changes" : "Submit Review";}
!{String authorValue = review != null && review.reviewAuthor() != null ? review.reviewAuthor() : "";}
!{String ratingValue = review != null && review.reviewRating() != null ? String.valueOf(review.reviewRating()) : "3";}
!{String reviewTextValue = review != null && review.reviewText() != null ? review.reviewText() : "";}

@template.layout(title = pageTitle, content = @`

    <div style="margin-bottom: 0.75rem;">
        <a href="/movies/${movieId}" style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.08em;">
            ← Back to Movie
        </a>
    </div>

    <div class="page-header">
        <h1 class="page-title">${pageTitle}</h1>
    </div>

    <form method="post" action="${formAction}" style="max-width: 680px;">

        <input type="hidden" name="movieId" value="${String.valueOf(movieId)}"/>

        <div class="form-group">
            <label for="reviewAuthor">Your Name</label>
            <input type="text" id="reviewAuthor" name="reviewAuthor" required value="${authorValue}"/>
        </div>

        <div class="form-group">
            <label for="reviewRating">Rating (1–5)</label>
            <div style="display: flex; align-items: center; gap: 1rem;">
                <input type="range" id="reviewRating" name="reviewRating" min="1" max="5" step="0.5"
                       value="${ratingValue}"
                       style="flex: 1;"
                       oninput="document.getElementById('ratingDisplay').textContent = this.value"/>
                <span id="ratingDisplay" style="font-family: var(--font-heading), serif; font-size: 1.5rem; color: var(--accent); min-width: 1.5rem;">
                    ${ratingValue}
                </span>
            </div>
        </div>

        <div class="form-group">
            <label for="reviewText">Review <span style="color: var(--text-muted); font-size: 0.7rem;">(optional)</span></label>
            <textarea id="reviewText" name="reviewText">${reviewTextValue}</textarea>
        </div>

        <div style="display: flex; gap: 0.75rem; margin-top: 0.5rem;">
            <button type="submit" class="btn btn-primary">${submitLabel}</button>
            <a href="/movies/${movieId}" class="btn btn-ghost">Cancel</a>
        </div>
    </form>
`)