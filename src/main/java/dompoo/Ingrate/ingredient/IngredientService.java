package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.ingredient.dto.IngredientAddRequest;
import dompoo.Ingrate.member.Member;
import dompoo.Ingrate.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    public void addIngredient(Long memberId, IngredientAddRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .name(request.getName())
                .cost(request.getCost())
                .amount(request.getAmount())
                .unit(request.getUnit())
                .memo(request.getMemo())
                .date(LocalDate.now())
                .build());

        ingredient.setMember(member);
        member.addPost(1);
        member.addPoint(1);
    }
}
